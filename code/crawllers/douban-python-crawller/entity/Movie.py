class Movie:
    def __init__(self, _id, name, _type, language, ranking,hot):
        self.id = _id
        self.name = name
        self.type = _type
        self.language = language
        self.ranking = ranking
        self.hot = hot

    def __str__(self):
        result = self.id+" "+self.name+" "+ self.type+" "+self.language+" "+self.ranking+" "+self.hot
        return result
