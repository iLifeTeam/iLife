import base64
import pickle
import re
from concurrent.futures import ThreadPoolExecutor
import requests
import os
import time
from urllib.parse import unquote
from bs4 import BeautifulSoup
import json
import math

executor = ThreadPoolExecutor(2)
qr_path = "./qr"
session_path = "./session"


def saveImg(img, path):
    if os.path.isfile(path):
        os.remove(path)
    fp = open(path, 'wb')
    fp.write(img)
    fp.close()


def showImg(path):
    from PIL import Image
    img = Image.open(path)
    img.show()
    img.close()


def createDirIfNotExists(path):
    if not os.path.exists(path):
        os.makedirs(path)


def saveSession(session, username):
    filename = session_path + "/" + username + ".pkl"
    print(filename)
    f = open(filename, 'wb')
    pickle.dump(session, f)
    f.close()


def recapSession(username):
    filename = session_path + "/" + username + ".pkl"
    if not os.path.exists(filename):
        return None
    f = open(filename, "rb")
    return pickle.load(f)


def fetchVariable(soup, name):
    pattern = re.compile(r"\$ORDER_CONFIG\['" + name + "'\]='(.*?)';")
    script = soup.find_all("script", text=pattern)
    return pattern.search(script[0].text).group(1)


def fetchYearOrders(session, year):
    orderListUrl = "https://order.jd.com/center/list.action?search=0&d={}&s=4096"
    orderInfoUrl = "https://order.jd.com/lazy/getOrderProductInfo.action"
    orderListResponse = session.get(orderListUrl.format(year))

    soup = BeautifulSoup(orderListResponse.content, "html5lib")
    # print(soup)
    orderWareIds = fetchVariable(soup, "orderWareIds")
    orderWareTypes = fetchVariable(soup, "orderWareTypes")
    orderIds = fetchVariable(soup, "orderIds")
    orderTypes = fetchVariable(soup, "orderTypes")
    orderSiteIds = fetchVariable(soup, "orderSiteIds")
    sendPays = fetchVariable(soup, "sendPays")
    if orderWareIds == "":
        return []
    post_body = {
        'orderWareIds': orderWareIds,
        'orderWareTypes': orderWareTypes,
        'orderIds': orderIds,
        'orderTypes': orderTypes,
        'orderSiteIds': orderSiteIds,
        'sendPays': sendPays
    }
    orderInfoResponse = session.post(orderInfoUrl, data=post_body)
    orderInfoJson = orderInfoResponse.json()
    orders = []
    orderIdList = orderIds.split(",")
    for orderId in orderIdList:
        exist = False
        for o in orders:
            if o["orderId"] == orderId:
                exist = True
                break
        if exist:
            continue
        numberList = soup.select("#tb-" + orderId + " > tr.tr-bd > td > div.goods-number")
        # print(numberList)
        order = {"price": soup.select("#tb-" + orderId + " > tr.tr-bd > td > div.amount > span")[0].text[1:],
                 "orderId": orderId, "shop": "",
                 "date": soup.select("#tb-" + orderId + " > tr > td > span.dealtime")[0].text,
                 "products": [],
                 "numberList": list(map(lambda node: node.text.strip().strip('\n')[1:], numberList))}
        orders.append(order)
    productIdList = orderWareIds.split(",")
    # print("product", productIdList)
    # print("order", orderIdList)
    for orderItem in orderInfoJson:
        product = {"img_url": orderItem['imgPath'], "productId": orderItem['productId'], "name": orderItem['name'],
                   "price": 0, "number": 0}
        index = productIdList.index(str(product["productId"]))
        print("product", product["productId"], ", index", index)
        for idx, order in enumerate(orders):
            if order["orderId"] == orderIdList[index]:
                ith = len(orders[idx]["products"])
                if ith < len(order["numberList"]):
                    product["number"] = order["numberList"][ith]
                else:
                    product["number"] = 1
                orders[idx]["products"].append(product)
                break
    # print(orders)
    return orders


class Jd:
    def __init__(self):
        self.info = 'jingdong'
        self.session = requests.Session()
        self.ticket_url = 'https://passport.jd.com/uc/qrCodeTicketValidation?t={}'
        self.token_url = 'https://qr.m.jd.com/check?callback=a&isNewVersion=1&_format_=json&appid=133&token={}'
        self.qrshow_url = 'https://qr.m.jd.com/show?appid=133&size=147&t={}'

        self.headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36',
            'Referer': 'https://passport.jd.com/new/login.aspx?ReturnUrl=https%3A%2F%2Fwww.jd.com%2F'
        }
        self.session.headers.update(self.headers)

    def getQrCode(self):
        return self.session.get(self.qrshow_url.format(int(time.time() * 1000)))

    def setSession(self, session):
        self.session = session

    def pendingForSession(self, uid):
        print("pending at: ", uid)
        token = self.session.cookies.get('wlfstk_smdl')
        while True:
            res = self.session.get(self.token_url.format(token))
            res_json = json.loads(res.text[2: -1])
            # --扫码成功
            if res_json['code'] == 200:
                ticket = res_json['ticket']
                res = self.session.get(self.ticket_url.format(ticket))
                res_json = res.json()
                if not res_json['returnCode']:
                    res = self.session.get(res_json['url'])
                username = self.session.cookies.get('pin', '')
                nickname = unquote(self.session.cookies.get('unick', ''))
                break
            # --等待扫码以及正在扫码
            elif res_json['code'] in [201, 202]:
                pass
            # --二维码过期
            elif res_json['code'] == 203:
                raise RuntimeError('Fail to login, qrcode has expired...')
            # --其他情况
            else:
                raise RuntimeError(res_json['msg'])
            time.sleep(1)
        # 登录成功
        print('[INFO]: Account -> %s, login successfully...' % nickname)
        saveSession(self.session, uid)
        return


def login(username):
    cur_path = os.getcwd()
    createDirIfNotExists(qr_path)
    createDirIfNotExists(session_path)
    jd = Jd()
    qrCode = jd.getQrCode()
    qrString = base64.b64encode(qrCode.content)
    saveImg(qrCode.content, qr_path + "/" + username + ".jpg")
    # showImg(qr_path + "1.jpg")
    executor.submit(jd.pendingForSession, username)
    return qrString
    # info, session = jingdong.pendingForSession(username)
    # print(info)
    # print(session.cookies)
    # saveSession(session, info['username'])


def fetch(username, years):
    session = recapSession(username)
    if session is None:
        return "not login"
    results = []
    for year in years:
        print(year)
        resp = fetchYearOrders(session, year)
        # print(json.dumps(resp,indent=2))
        results = results + resp
        time.sleep(2)
    # print(results)
    return results


# if __name__ == '__main__':
#     resp = fetchYearOrders(session, 2020)
#     print(json.dumps(resp, indent=2))
def checkLogin(username):
    session = recapSession(username)
    if session is None:
        return "not login"
    else:
        return "login"
