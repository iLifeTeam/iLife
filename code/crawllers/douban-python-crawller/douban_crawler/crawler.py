import requests
import multiprocessing
from bs4 import BeautifulSoup, NavigableString, Tag
from absl import app
from douban_crawler import config_parser
from const.const import BASE_URL, MOVIE_URL
from entity.Movie import Movie
from writer.mysql_writer import Mysqlwriter


class Crawler:
    def __init__(self, _id):
        # load config
        config = config_parser.Config()
        config.get_config()
        self.config = config
        self.id = _id
        self.cookies = config.cookies
        self.headers = {
            'User-Agent': 'Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR '
                          '2.0.50727) ',
            # 'Cookie': self.cookies
        }

    def crawl(self, prefix, postfix):
        url = prefix + str(self.id) + postfix
        print(url)
        r = requests.get(url,
                         cookies=self.cookies,
                         headers=self.headers)
        return r.text

    def parse_movies(self, text):
        st_movies = []
        print(self.id)
        soup = BeautifulSoup(text, 'lxml')
        movie_head = soup.find(class_='grid-view')
        movie_list = movie_head.children
        for movie in movie_list:
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
                if content.string is None:
                    ranking = str(0)
                else:
                    ranking = content.string
                content = movie_soup.find(class_=["rating_sum"])
                # some people will mark not
                print(content.contents[0].strip())
                if str(content.contents[0]).strip() == "尚未上映":
                    hot = str(0)
                else:
                    hot = content.a.span.string
                print(name,_type,language,ranking,hot)
                movie = Movie(self.id, name, _type, language, ranking, hot)
                st_movies.append(movie)
                print(movie)
        mysqlwriter = Mysqlwriter(self.config)
        mysqlwriter.write_movie(st_movies)

    def work(self, prefix, postfix):
        text = self.crawl(prefix, postfix)
        self.parse_movies(text)


def main(_id, _type, movie_page):
    crawler = Crawler(_id)
    # the real page number is in class_ = paginator
    if _type == "movie":
        # TODO:add multiprocessing support
        pool = multiprocessing.Pool(processes=4)
        data_list = []
        for i in range(int(movie_page)):
            data_list.append((MOVIE_URL, "/collect?start=" + str(30+15 * i)))
        print(data_list)
        res = pool.starmap(crawler.work, data_list)
        pool.close()
        pool.join()
        # print(data_list)


if __name__ == '__main__':
    app.run(main)
