import requests
import multiprocessing
import time
import json
from bs4 import BeautifulSoup, NavigableString, Tag
from absl import app
import random
from douban_crawler import config_parser
from const.const import BASE_URL, MOVIE_URL, BOOK_URL
from entity.Movie import Movie
from entity.Book import Book
from entity.User import User
from entity.MovieRcmd import MovieRcmd
from entity.BookRcmd import BookRcmd
from writer.mysql_writer import Mysqlwriter


class CrawlerRcmd:
    def __init__(self):
        # load config
        self.headers = {
            'User-Agent': 'PostmanRuntime/7.26.1',
            'Connection': 'keep-alive',
            'Cookie': 'bid=ZN1dBm-0o9g; douban-fav-remind=1; ll="118201"; '
                      '_vwo_uuid_v2=D58A7FEF07D6C3A2E2FAB8E378796D6C6|3174d2e6e85c8111e9144960f76088fe; '
                      'gr_user_id=3c33da18-73de-42a4-8cbf-57af0bfa5aba; push_doumail_num=0; __utmv=30149280.13208; '
                      'douban-profile-remind=1; _ga=GA1.2.193287346.1588984522; viewed="35080696_26284925"; apiKey=; '
                      '__utmc=30149280; ct=y; __utmz=30149280.1597749914.48.23.utmcsr=google|utmccn=('
                      'organic)|utmcmd=organic|utmctr=(not%20provided); ap_v=0,'
                      '6.0; __utma=30149280.193287346.1588984522.1597749914.1597753310.49; user_data={'
                      '%22area_code%22:%22+86%22%2C%22number%22:%2213559249209%22%2C%22code%22:%221859%22}; '
                      'vtoken=undefined; _pk_ref.100001.2fad=%5B%22%22%2C%22%22%2C1597756273%2C%22https%3A%2F%2Fmovie'
                      '.douban.com%2Fphotos%2Fphoto%2F2548750147%2F%22%5D; _pk_ses.100001.2fad=*; '
                      'last_login_way=account; dbcl2="132088467:kvNkDZUNfgY"; ck=L6VY; __utmt=1; '
                      '__utmb=30149280.6.10.1597753310; push_noty_num=0; '
                      '_pk_id.100001.2fad=6e322d9fc83ea1ec.1595382445.7.1597756602.1596004253.; '
                      'login_start_time=1597756603899 '
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
                    continue
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

    def work_movie(self, movieTagList, attitude, queue, hashtag, lock):
        movie_all_List = []
        high_rate = hot = 0
        picture = movie_soup = rate = title = url = introduction = type = actors_list = content = ""
        startNum = (hashtag % 7) * 20
        for tag in movieTagList:
            if tag == "豆瓣高分":
                high_rate = 1
                continue
            if tag == "冷门佳片":
                hot = 0
                continue
            if tag == "热门":
                hot = 1
                continue
            movie_url = 'https://movie.douban.com/j/search_subjects?type=movie&tag=' + str(
                tag) + '&sort=recommend&page_limit=20&page_start=' + str(startNum)
            movie_page = requests.get(movie_url, headers=self.headers)
            movie_list = movie_page.json()['subjects']
            movie = movie_list[hashtag % 20]
            # if user like high_rating movie, don't recommend low_rating movie to them
            for i in range(20):
                movie = movie_list[(hashtag + i) % 20]
                if high_rate == 1 and int(movie['rate']) <= 7 and not i == 19:
                    continue
                else:
                    title = movie['title']
                    url = movie['url']
                    rate = movie['rate']
                    movie_page = requests.get(url, headers=self.headers)
                    movie_soup = BeautifulSoup(movie_page.text, 'lxml')
                    content = movie_soup.find(id="content")
                    if content is None and not i == 19:
                        continue
                    else:
                        break
            content = content.find(id="info")
            actors = content.find(class_="actor")
            actors = actors.find(class_="attrs")
            for actor in actors.contents:
                if isinstance(actor, NavigableString) or actor is None:
                    continue
                actors_list += actor.string.strip()
                actors_list += '/'
            # ------------------------------------
            content = movie_soup.find(id="info")
            info_list = content.contents

            for info in info_list:
                if isinstance(info, Tag):
                    if info.has_attr('property') and not info.has_attr('content'):
                        if type == "":
                            type += info.string
                        else:
                            type += (" / " + info.string)
            # -------------------------------------
            content = movie_soup.find(id="link-report")
            for con in content.span.span.children:
                if con is None or isinstance(con, Tag):
                    continue
                else:
                    introduction += con.strip()
            content = movie_soup.find(class_='nbgnbg')
            pic_url = content['href']
            print(pic_url)
            pic_page = requests.get(pic_url, headers=self.headers)
            pic_soup = BeautifulSoup(pic_page.text, 'lxml')
            content = pic_soup.find(class_=['poster-col3', 'clearfix'])
            picture = content.li.div.a
            picture_url = picture['href']
            print(picture_url)
            pic_origin_page = requests.get(picture_url, headers=self.headers)
            pic_origin_soup = BeautifulSoup(pic_origin_page.text, 'lxml')
            content = pic_origin_soup.find(class_=['update', 'magnifier'])
            picture = content.a['href']
            print(picture)
            movie_all_List.append(MovieRcmd(title.strip(), rate.strip(), url, introduction, type.strip(), actors_list, picture))
        for movie in movie_all_List:
            lock.acquire()
            queue.put(movie_all_List[0].__dict__, block=False)
            lock.release()
        print("finish movie rcmd")
        return 1

    def work_book(self, bookTagList, preAuthor, attitude, queue, hashtag, lock):

        book_all_List = []
        tag_list = ['推理', '随笔', '理财', '科普', '武侠', '历史', '小说']
        high_rate = hot = 0
        book_url2 = ""
        picture = movie_soup = rate = title = url = introduction = author = price = content = ""
        startNum = (hashtag % 7) * 20
        for tag in bookTagList:
            if tag == "新书速递":
                hot = 0
                continue
            if tag == "畅销图书":
                hot = 1
                continue
        book_url2 = 'https://book.douban.com/tag/' + tag_list[hashtag % 7] + '?start=' + str(startNum)
        pic_page = requests.get(book_url2, headers=self.headers)
        pic_soup = BeautifulSoup(pic_page.text, 'lxml')
        content = pic_soup.find(class_='subject-list')

        content = content.find_all('li')
        book_url = content[hashtag % 7].find('h2').a
        title = book_url['title']
        book_url = book_url['href']
        url = book_url
        pic_page = requests.get(book_url, headers=self.headers)
        book_soup = BeautifulSoup(pic_page.text, 'lxml')
        content = book_soup.find(id="info")
        author = str(content.a.string).replace('\n', "").strip()
        for _info in content.children:
            if not isinstance(_info, Tag):
                continue
            if _info.string == "定价:":
                price = str(_info.next_sibling).replace('\n', "").strip()
        content = book_soup.find(class_="ll rating_num")
        if str(content.string).strip() == "":
            rate = str(0)
        else:
            rate = content.string.strip()

        content = book_soup.find(class_="rating_sum")
        if str(content.span.string).strip() == "目前无人评价":
            hot = str(0)
        else:
            hot = content.span.a.span.string.strip()
        content = book_soup.find(id='link-report')
        introduction_tag = content.find(class_='intro')
        for p in introduction_tag.contents:
            if p is None or str(p) == "<br/>":
                continue
            introduction += p.string

        content = book_soup.find(id='mainpic')
        picture = content.a['href']
        bookRcmd = BookRcmd(title, rate, url, introduction, author, price, picture, hot)
        book_all_List.append(bookRcmd)
        lock.acquire()
        queue.put(bookRcmd.__dict__)
        lock.release()
        print("finish book rcmd")
        return 1

    def work_music(self, musicTag, queue, hashtag, lock):
        lock.acquire()
        queue.put({"music": 'none'}, block=False)
        lock.release()
        print("finish music rcmd")
        return 1

    def work_game(self, gameTag, queue, hashtag, lock):
        lock.acquire()
        queue.put({"game": 'none'}, block=False)
        lock.release()
        print("finish game rcmd")
        return 1


def main(bookTagList, preAuthor, movieTagList, musicTag, gameTag, attitude, hashTag):
    lock = multiprocessing.Lock()
    queue = multiprocessing.Queue()
    crawler = CrawlerRcmd()
    # use multiprocessing to parallel the recommendation process
    process_book = multiprocessing.Process(target=crawler.work_book,
                                           args=(bookTagList, preAuthor, attitude, queue, hashTag, lock))
    process_movie = multiprocessing.Process(target=crawler.work_movie,
                                            args=(movieTagList, attitude, queue, hashTag, lock))
    process_music = multiprocessing.Process(target=crawler.work_music, args=(musicTag, queue, hashTag, lock))
    process_game = multiprocessing.Process(target=crawler.work_game, args=(gameTag, queue, hashTag, lock))
    process_book.start()
    process_movie.start()
    process_game.start()
    process_music.start()
    process_music.join()
    process_game.join()
    process_movie.join()
    process_book.join()
    results = {}
    print(queue.qsize())
    print('---------------------------------------')
    for i in range(0, 4):
        print("将要取出前的队列大小", queue.qsize())
        c = queue.get(False)
        print("取出元素", c)
        print("取出后的队列大小", queue.qsize())
        results.update(c)
    print('---------------------------------------')
    print(results)
    return results


if __name__ == '__main__':
    app.run(main)
