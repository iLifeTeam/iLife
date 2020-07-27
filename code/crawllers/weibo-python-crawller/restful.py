from flask import Flask, request, render_template
from weibo_spider import crawl
from flask_ext import *
import os

app = Flask(__name__)


@app.route('/weibo/crawlWeibo', methods=['GET'])
def login():
    userId = request.args.get('userId')
    startDate = request.args.get('startDate')
    endDate = request.args.get('endDate')
    print(userId)
    crawler = crawl.Crawl(userId, startDate, endDate)
    crawler.read_json()
    crawler.crawl()
    return "1"


if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)
