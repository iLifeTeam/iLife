import pymysql
import sys
import os
from douban_crawler.config_parser import Config


class Mysqlwriter:
    def __init__(self, config):
        self.host = config.host
        self.port = config.port
        self.user = config.user
        self.database = config.database
        self.password = config.password
        self.charset = config.charset
        self.conn = pymysql.connect(host=self.host, user=self.user, password=self.password, database=self.database,
                                    charset=self.charset)
        self.cursor = self.conn.cursor()

    def write_book(self, books):
        for book in books:
            values = ",".join(
                ["'" + book.id + "'", "'" + book.name + "'", "'" + book.author + "'",
                 "'" + str(book.price).strip() + "'",
                 book.ranking, book.hot])
            keys = ["id", "name", "author", "price", "ranking", "hot"]
            sql = """INSERT INTO {table}({keys}) VALUES ({values}) ON
                                    DUPLICATE KEY UPDATE""".format(table="book",
                                                                   keys=",".join(keys),
                                                                   values=values)
            update = ",".join([
                " {key} = values({key})".format(key=key)
                for key in keys
            ])
            sql += update
            print(sql)
            self.cursor.execute(sql)
        self.conn.commit()
        self.cursor.close()
        self.conn.close()

    def write_movie(self, movies):
        for movie in movies:
            values = ",".join(
                ["'" + movie.id + "'", "'" + movie.name + "'", "'" + movie.type + "'", "'" + movie.language + "'",
                 movie.ranking, movie.hot])
            keys = ["id", "name", "type", "language", "ranking", "hot"]
            sql = """INSERT INTO {table}({keys}) VALUES ({values}) ON
                                    DUPLICATE KEY UPDATE""".format(table="movie",
                                                                   keys=",".join(keys),
                                                                   values=values)
            update = ",".join([
                " {key} = values({key})".format(key=key)
                for key in keys
            ])
            sql += update
            print(sql)
            self.cursor.execute(sql)
        self.conn.commit()
        self.cursor.close()
        self.conn.close()

    def test(self):
        keys = ["id", "name", "author", "price", "ranking", "hot"]
        sql = """INSERT INTO {table}({keys}) VALUES ({values}) ON
                                DUPLICATE KEY UPDATE""".format(table="book",
                                                               keys=",".join(keys),
                                                               values="1,2,3,4,5")
        update = ",".join([
            " {key} = values({key})".format(key=key)
            for key in keys
        ])
        sql += update
        print(sql)
        self.cursor.execute(sql)
        self.conn.commit()
        self.cursor.close()
        self.conn.close()


if __name__ == '__main__':
    sys.path.append(os.path.abspath(os.path.dirname(os.getcwd())))
    config = Config()
    config.get_config()
    mysqlwriter = Mysqlwriter(config)
    mysqlwriter.test()
