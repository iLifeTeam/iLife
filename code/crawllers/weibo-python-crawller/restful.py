from flask import Flask,  request, render_template
from weibo_spider import crawl
from flask_ext import *
import os

app = Flask(__name__)


@app.route('/weibo/crawlWeibo', methods=['POST','GET'])
def login():
    if request.method == 'POST':
        if 'user' in request.json:
            print('Admin login successfully!')
            crawler = crawl.Crawl(request.json['user'])
            crawler.read_json()
            crawler.crawl()
            return "1"
        else:
            return 'illegal'
    else:
        userId=request.args.get('userId')
        crawler = crawl.Crawl(userId)
        crawler.read_json()
        crawler.crawl()
        return "1"


if __name__ == "__main__":
    app.run(debug=True)
