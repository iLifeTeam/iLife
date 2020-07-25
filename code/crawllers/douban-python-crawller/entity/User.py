class User:
    def __init__(self, _id, name, movie_wish, movie_watched, book_wish, book_read, comment, following, follower):
        self.id = _id
        self.name = name
        self.movie_wish = movie_wish
        self.movie_watched = movie_watched
        self.book_wish = book_wish
        self.book_read = book_read
        self.comment = comment
        self.following = following
        self.follower = follower

    def __str__(self):
        result = self.id + ' ' + self.name + " " + self.movie_wish + " " + self.movie_watched + " " + self.book_wish \
                 + " " + self.book_read + " " + self.comment + " " + self.following + " " + self.follower
        return result
