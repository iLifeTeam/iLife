import json


class Config:
    def __init__(self):
        # default values
        self.cookies = {"Cookie": 'bid=ZN1dBm-0o9g; douban-fav-remind=1; '
                                  '__yadk_uid=clm1BwAvEboA5xjReL4Q8gVlnWiKcsfB; '
                                  'll="118201"; '
                                  '_vwo_uuid_v2=D58A7FEF07D6C3A2E2FAB8E378796D6C6'
                                  '|3174d2e6e85c8111e9144960f76088fe; '
                                  'viewed="26284925"; gr_user_id=3c33da18-73de-42a4-8cbf-57af0bfa5aba; '
                                  'trc_cookie_storage=taboola%2520global%253Auser-id%3Da9a4e48b-377b-4b8d'
                                  '-8603 '
                                  '-969341741197-tuct59bdd00; '
                                  '_pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1595381211%2C%22https%3A%2F'
                                  '%2Fwww.google '
                                  '.com%2F%22%5D; _pk_ses.100001.8cb4=*; '
                                  '__utmz=30149280.1595381455.15.12.utmcsr=google|utmccn=('
                                  'organic)|utmcmd=organic|utmctr=(not%20provided); __utmc=30149280; '
                                  '__utma=30149280.193287346.1588984522.1594909739.1595381455.15; ap_v=0,'
                                  '6.0; push_noty_num=0; push_doumail_num=0; __utmv=30149280.13208; '
                                  'douban-profile-remind=1; __utmt=1; dbcl2="132088467:mcj0gbzrFD0"; '
                                  'ck=y2F9; '
                                  '_pk_id.100001.8cb4=be2879f9b43cfd40.1588984520.12.1595382716.1594912731.; '
                                  '__utmb=30149280.39.10.1595381455'},
        self.id = 132088441
        self.host = "127.0.0.1"
        self.port = 3306
        self.user = "ilife"
        self.password = "ilife2020"
        self.charset = "utf8mb4"
        self.database = "douban"

    # get configuration from config.json rather than use default value
    def get_config(self):
        # current folder is project base folder
        config = open("config.json", encoding="utf-8")
        dic = json.load(config)
        self.user = dic['user']
        self.cookies = {"Cookie": dic['cookies']}
        self.id = dic['id']
        self.host = dic['host']
        self.port = dic['port']
        self.password = dic['password']
        self.charset = dic['charset']
        self.database = dic['database']
