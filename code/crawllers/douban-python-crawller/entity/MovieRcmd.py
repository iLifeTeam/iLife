class MovieRcmd:
    def __init__(self, title, rate, url, introduction, _type, actors_list, picture):
        self.title_movie = title
        self.rate_movie = rate
        self.type_movie = _type
        self.url_movie = url
        self.introduction_movie = introduction
        self.actors_list_movie = actors_list
        self.picture_movie = picture

    # def __str__(self):
    #     result = self.title + " " + self.rate + " " + self.type + " " + self.url + " " + self.introduction + " " + self.actors_list + " " + self.picture
    #     return result
