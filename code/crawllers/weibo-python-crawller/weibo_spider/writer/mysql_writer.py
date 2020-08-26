import copy
import sys
import traceback

from .writer import Writer


class MySqlWriter(Writer):
    def __init__(self, mysql_config, userId, cookies):
        self.headers = {
            'User-Agent': 'PostmanRuntime/7.26.1',
            'Connection': 'keep-alive'
        }
        self.mysql_config = mysql_config
        self.userId = userId
        self.cookies = cookies
        # 创建'weibo'数据库
        create_database = """CREATE DATABASE IF NOT EXISTS weibo DEFAULT
                            CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci"""
        self._mysql_create_database(create_database)
        self.mysql_config["db"] = "weibo"

    def _mysql_create(self, connection, sql):
        """创建MySQL数据库或表"""
        try:
            with connection.cursor() as cursor:
                cursor.execute(sql)
        finally:
            connection.close()

    def _mysql_create_database(self, sql):
        """创建MySQL数据库"""
        try:
            import pymysql
        except ImportError:
            sys.exit(u"系统中可能没有安装pymysql库，请先运行 pip install pymysql ，再运行程序")
        try:
            print(self.mysql_config, sql)
            connection = pymysql.connect(**self.mysql_config)
            self._mysql_create(connection, sql)
        except pymysql.OperationalError:
            sys.exit(u"系统中可能没有安装或正确配置MySQL数据库，请先根据系统环境安装或配置MySQL，再运行程序")

    def _mysql_create_table(self, sql):
        """创建MySQL表"""
        import pymysql
        connection = pymysql.connect(**self.mysql_config)
        self._mysql_create(connection, sql)

    def crawlPic(self):
        print(self.userId[0])
        # url = 'https://weibo.com/u/' + str(self.userId[0])+'?is_hot=1'
        # home_page = requests.request('GET', url, cookies=self.cookies, headers=self.headers)
        # book_soup = BeautifulSoup(home_page.text, 'lxml')
        # print(book_soup.prettify())
        return self.userId

    def _mysql_insert(self, table, data_list):
        """向MySQL表插入或更新数据"""
        import pymysql
        picUrl = self.crawlPic()
        if len(data_list) > 0:
            # We use this to filter out unset values.
            data_list = [{k: v
                          for k, v in data.items() if v is not None}
                         for data in data_list]

            keys = ", ".join(data_list[0].keys())
            values = ", ".join(["%s"] * len(data_list[0]))
            connection = pymysql.connect(**self.mysql_config)
            cursor = connection.cursor()
            sql = """INSERT INTO {table}({keys}) VALUES ({values}) ON
                        DUPLICATE KEY UPDATE""".format(table=table,
                                                       keys=keys,
                                                       values=values)
            update = ",".join([
                " {key} = values({key})".format(key=key)
                for key in data_list[0]
            ])
            sql += update
            try:
                cursor.executemany(
                    sql, [tuple(data.values()) for data in data_list])
                connection.commit()
            except Exception as e:
                connection.rollback()
                print("Error: ", e)
                traceback.print_exc()
            finally:
                connection.close()

    def write_weibo(self, weibos):
        """将爬取的微博信息写入MySQL数据库"""
        weibo_list = []
        info_list = copy.deepcopy(weibos)
        for weibo in info_list:
            weibo.user_id = self.user.id
            weibo_list.append(weibo.__dict__)
        self._mysql_insert("weibo", weibo_list)
        print(u"%d条微博写入MySQL数据库完毕" % len(weibos))

    def write_user(self, user):
        """将爬取的用户信息写入MySQL数据库"""
        self.user = user
        self._mysql_insert("user", [user.__dict__])
        print(u"%s信息写入MySQL数据库完毕" % user.nickname)
