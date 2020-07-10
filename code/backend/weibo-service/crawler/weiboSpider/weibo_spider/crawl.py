import os

print(os.path.dirname(os.path.abspath('../setup.py')))
# 部署在服务器上时，应该将绝对地址改为服务器上的绝对地址
os.chdir('F:\\Documentation\\Courseware\\ilfe 2020\\iLife\\code\\backend\\weibo-service\\crawler\\weiboSpider')
os.system('python -m weibo_spider');