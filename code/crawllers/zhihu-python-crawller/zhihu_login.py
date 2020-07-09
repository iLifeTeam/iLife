from zhihu_oauth import ZhihuClient
from zhihu_oauth.exception import NeedCaptchaException

client = ZhihuClient()

try:
    client.login('zxy771906409@163.com', 'zxy13,./0904')
except NeedCaptchaException:
    with open('a.gif', 'wb') as f:
        f.write(client.get_captcha())
    captcha = input('please input captcha:')
    client.login('zxy771906409@163.com', 'zxy13,./0904', captcha)

client.save_token('token.pkl')