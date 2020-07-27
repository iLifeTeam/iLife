class Book:
    def __init__(self, _id, name, author, price, ranking, hot):
        self.id = _id
        self.name = name
        self.author = author
        self.price = price
        self.ranking = ranking
        self.hot = hot

    def __str__(self):
        result = self.id + " " + self.name + " " + self.author + " " + self.price + " " + self.ranking + " " + self.hot
        return result
