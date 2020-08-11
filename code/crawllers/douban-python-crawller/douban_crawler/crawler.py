import requests
import multiprocessing
import time
from bs4 import BeautifulSoup, NavigableString, Tag
from absl import app
import random
from douban_crawler import config_parser
from const.const import BASE_URL, MOVIE_URL, BOOK_URL
from entity.Movie import Movie
from entity.Book import Book
from entity.User import User
from writer.mysql_writer import Mysqlwriter


class Crawler:
    def __init__(self, _id):
        # load config
        config = config_parser.Config()
        config.get_config()
        self.config = config
        self.id = _id
        # self.proxies = {
        #     'http': 'http://191.235.72.244:8080',
        #     'https': 'http://191.235.72.244:8080',
        # }
        self.cookies = config.cookies
        self.headers = {
            'User-Agent': 'PostmanRuntime/7.26.1',
            'Connection': 'keep-alive',
            'Cookie': 'bid=ZN1dBm-0o9g; douban-fav-remind=1; ll="118201"; '
                      '_vwo_uuid_v2=D58A7FEF07D6C3A2E2FAB8E378796D6C6|3174d2e6e85c8111e9144960f76088fe; '
                      'viewed="26284925"; gr_user_id=3c33da18-73de-42a4-8cbf-57af0bfa5aba; __utmc=30149280; '
                      'push_noty_num=0; push_doumail_num=0; __utmv=30149280.13208; douban-profile-remind=1; '
                      '__utmz=30149280.1595593664.26.16.utmcsr=accounts.douban.com|utmccn=(referral)|ut '
                      'mcmd=referral|utmcct=/passport/login; '
                      '__utma=30149280.193287346.1588984522.1595920301.1595925657.32; dbcl2="132088467:jgUxT5YQvoI"; '
                      'ck=w7o8; __utmt_douban=1; __utmb=30149280.16.10.1595925657 '
        }

    def crawl(self, prefix, postfix):
        url = prefix + str(self.id) + postfix
        print(url)

        r = requests.get(url,
                         # proxies=self.proxies,
                         cookies=self.cookies,
                         headers=self.headers)
        return r.text

    def parse_user(self, text):
        soup = BeautifulSoup(text, 'lxml')
        user_head = soup.find(class_="article")
        if user_head is None:
            raise Exception
        content = user_head.div.find(class_="info")
        name = str(content.h1.contents[0]).replace("\"", " ").strip()
        # get movies 注意有些人没有“在看”
        movie_wish = movie_watched = str(0)
        book_wish = book_read = str(0)
        content = user_head.find(id="movie")
        if not str(content.string).strip() == "":
            content = content.h2.span
            for _text in content.contents:
                if _text.string[-2] == "想":
                    movie_wish = str(_text.string)[0:len(_text.string) - 3]
                if _text.string[-2] == "看":
                    movie_watched = str(_text.string)[0:len(_text.string) - 3]
        # get books
        content = user_head.find(id="book")
        if not str(content.string).strip() == "":
            content = content.h2.span
            for _text in content.contents:
                if _text.string[-2] == "想":
                    book_wish = str(_text.string)[0:len(_text.string) - 3]
                if _text.string[-2] == "读":
                    book_read = str(_text.string)[0:len(_text.string) - 3]
        content = user_head.find(id="review")
        comment = str(0)
        if not str(content.string).strip() == "":
            comment = content.h2.span.a.string[2:]
        content = soup.find(id="friend")
        following = str(0)
        if not str(content.string).strip() == "":
            following = content.h2.span.a.string[2:]
        content = soup.find(class_="rev-link")
        follower = content.a.string[len(name) + 3:-3]
        print(follower)
        user = User(self.id, name, movie_wish, movie_watched, book_wish, book_read, comment, following, follower)
        print(user)
        mysqlwriter = Mysqlwriter(self.config)
        mysqlwriter.write_user(user)

    def parse_books(self, text):
        st_books = []
        soup = BeautifulSoup(text, 'lxml')
        book_head = soup.find(class_="interest-list")
        for book in book_head.children:
            # average sleeping time:
            rad = random.randint(0, 7)
            if rad == 3 or rad == 4:
                time.sleep(1)
            if rad == 5 or rad == 6:
                time.sleep(2)
            if rad == 7:
                time.sleep(4)
            if rad == 8:
                time.sleep(5)
            price = ""
            hot = ""
            if not isinstance(book, Tag):
                continue
            book_info = book.find(class_="info")
            book_url = book_info.h2.a['href']
            book_page = requests.get(book_url, cookies=self.cookies, headers=self.headers)
            book_soup = BeautifulSoup(book_page.text, 'lxml')
            content = book_soup.find(id="wrapper")
            name = str(content.h1.span.string).strip()
            content = book_soup.find(id="info")
            author = str(content.a.string).replace('\n', "").strip()
            for _info in content.children:
                if not isinstance(_info, Tag):
                    continue
                if _info.string == "定价:":
                    price = str(_info.next_sibling).replace('\n', "").strip()
            content = book_soup.find(class_="ll rating_num")
            if str(content.string).strip() == "":
                ranking = str(0)
            else:
                ranking = content.string
            content = book_soup.find(class_="rating_sum")
            if str(content.span.string).strip() == "目前无人评价":
                hot = str(0)
            else:
                hot = content.span.a.span.string
            book = Book(self.id, name, author, price, ranking, hot)
            st_books.append(book)
            print(book)
        mysqlwriter = Mysqlwriter(self.config)
        mysqlwriter.write_book(st_books)

    def parse_movies(self, text):
        st_movies = []
        print(self.id)
        soup = BeautifulSoup(text, 'lxml')
        movie_head = soup.find(class_='grid-view')
        movie_list = movie_head.children
        for movie in movie_list:
            rad = random.randint(0, 8)
            if rad == 3 or rad == 4:
                time.sleep(1)
            if rad == 5 or rad == 6:
                time.sleep(2)
            if rad == 7:
                time.sleep(4)
            if rad == 8:
                time.sleep(5)
            if isinstance(movie, NavigableString):
                continue
            if isinstance(movie, Tag):
                # name type language rank
                _type = ""
                language = ""
                # start parsing
                movie_info = movie.find('ul')
                movie_url = movie_info.li.a['href']
                movie_page = requests.get(movie_url, cookies=self.cookies, headers=self.headers)
                movie_soup = BeautifulSoup(movie_page.text, 'lxml')
                content = movie_soup.find(id="content")
                if content is None:
                    print("first parsing FAILED!")
                    print(movie_url)
                name = content.h1.span.string
                content = movie_soup.find(id="info")
                info_list = content.contents

                for info in info_list:
                    if isinstance(info, Tag):
                        if info.has_attr('property') and not info.has_attr('content'):
                            if _type == "":
                                _type += info.string
                            else:
                                _type += (" / " + info.string)
                        if info.string == "语言:":
                            language = info.next_sibling
                content = movie_soup.find(class_=["ll rating_num"])
                if content is None or content.string is None:
                    ranking = str(0)
                else:
                    ranking = content.string
                content = movie_soup.find(class_=["rating_sum"])
                # some people will mark not
                if str(content.contents[0]).strip() == "尚未上映" or str(content.contents[0]).strip() == "暂无评分":
                    hot = str(0)
                else:
                    hot = content.a.span.string
                movie = Movie(self.id, name, _type, language, ranking, hot)
                st_movies.append(movie)
                print(movie)
        mysqlwriter = Mysqlwriter(self.config)
        mysqlwriter.write_movie(st_movies)

    def work_movie(self, prefix, postfix, sleepTime):
        time.sleep(sleepTime)
        text = self.crawl(prefix, postfix)
        self.parse_movies(text)

    def work_book(self, prefix, postfix, sleepTime):
        time.sleep(sleepTime)
        text = self.crawl(prefix, postfix)
        self.parse_books(text)

    def work_user(self, prefix, postfix):
        text = self.crawl(prefix, postfix)
        self.parse_user(text)

    def real_page_movie(self, page):
        url = MOVIE_URL + str(self.id) + "/collect?start=0"
        r = requests.get(url,
                         cookies=self.cookies,
                         headers=self.headers)
        soup = BeautifulSoup(r.text, "lxml")
        content = soup.find(class_="paginator")
        if not content is None:
            span = content.find(class_="thispage")
            total_page = span["data-total-page"]
            if int(page) > int(total_page):
                return total_page
            else:
                return page
        else:
            return 1


def main(_id, _type, page):
    crawler = Crawler(_id)
    # the real page number is in class_ = paginator
    if _type == "movie":
        pool = multiprocessing.Pool(processes=4)
        data_list = []
        page = crawler.real_page_movie(page)
        for i in range(int(page)):
            randomInt = random.randint(2, min(int(i) + 2, 10))
            data_list.append((MOVIE_URL, "/collect?start=" + str(15 * i), randomInt))
        print(data_list)
        res = pool.starmap(crawler.work_movie, data_list)
        pool.close()
        pool.join()
        # print(data_list)
    if _type == "book":
        pool = multiprocessing.Pool(processes=4)
        data_list = []
        # book_page = crawler.real_page_bookpage
        for i in range(int(page)):
            randomInt = random.randint(0, min(int(page), 8))
            print(randomInt)
            data_list.append((BOOK_URL, "/collect?start=" + str(15 * i), randomInt))
        print(data_list)
        res = pool.starmap(crawler.work_book, data_list)
        pool.close()
        pool.join()
        # print(data_list)
    if _type == "user":
        crawler.work_user(BASE_URL, "")


if __name__ == '__main__':
    app.run(main)
