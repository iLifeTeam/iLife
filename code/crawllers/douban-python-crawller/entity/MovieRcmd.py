class MovieRcmd:
    def __init__(self, title, rate, url, introduction, _type, actors_list, picture, comment):
        self.title = title
        self.rate = rate
        self.type = _type
        self.url = url
        self.introduction = introduction
        self.actors_list = actors_list
        self.picture = picture
        self.comment = comment

    def __str__(self):
        result = self.title + " " + self.rate + " " + self.type + " " + self.url + " " + self.introduction + " " + self.actors_list + " " + self.picture + " " + self.comment
        return result
