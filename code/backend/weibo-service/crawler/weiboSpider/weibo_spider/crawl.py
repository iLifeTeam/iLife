import os
import sys
import json


class Crawl:
    def __init__(self):
        self.id = sys.argv[1]

    def crawl(self):
        # TODO:部署在服务器上时，应该将绝对地址改为服务器上的绝对地址
        os.chdir('F:\\Documentation\\Courseware\\ilfe 2020\\iLife\\code\\backend\\weibo-service\\crawler\\weiboSpider')
        os.system('python -m weibo_spider')

    def read_json(self):
        with open('F:\\Documentation\\Courseware\\ilfe 2020\\iLife\\code\\backend\\weibo-service\\crawler\\weiboSpider\\config.json', 'r', encoding='utf8')as fp:
            json_data = json.load(fp)
            array = [str(self.id)]
            json_data["user_id_list"] = array
        with open('F:\\Documentation\\Courseware\\ilfe 2020\\iLife\\code\\backend\\weibo-service\\crawler\\weiboSpider\\config.json', 'w', encoding='utf8')as fp:
            fp.write(json.dumps(json_data))


if __name__ == '__main__':
    try:
        crawl = Crawl()
        crawl.read_json()
        crawl.crawl()
    except Exception as e:
        print('Error: ', e)
