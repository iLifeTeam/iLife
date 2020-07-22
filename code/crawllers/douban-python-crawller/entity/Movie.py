class Movie:
    def __init__(self, _id, name, _type, language, ranking):
        self.id = _id
        self.name = name
        self.type = _type
        self.language = language
        self.ranking = ranking

    def __str__(self):
        result = self.id+" "+self.name+" "+ self.type+" "+self.language+" "+self.ranking
        return result
