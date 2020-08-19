class MovieRcmd:
    def __init__(self, title, rate, url, introduction, _type, actors_list, picture):
        self.title = title
        self.rate = rate
        self.type = _type
        self.url = url
        self.introduction = introduction
        self.actors_list = actors_list
        self.picture = picture

    def __str__(self):
        result = self.title + " " + self.rate + " " + self.type + " " + self.url + " " + self.introduction + " " + self.actors_list + " " + self.picture
        return result
