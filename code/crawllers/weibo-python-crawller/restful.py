from flask import Flask, request, render_template
from weibo_spider import crawl
from flask_ext import *
import os
from flask_cors import CORS

app = Flask(__name__)

cors = CORS(app, resources={r"/*": {"origins": ["http://49.234.125.131", "http://localhost:3000"]}})


@app.after_request
def after_request(response):
    # response.headers.add('Access-Control-Allow-Origin', 'http://localhost:3000')
    response.headers.add('Access-Control-Allow-Headers', 'Content-Type,Authorization')
    response.headers.add('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE,OPTIONS')
    response.headers.add('Access-Control-Allow-Credentials', 'true')
    return response


@app.route('/weibo/crawlWeibo', methods=['GET'])
def login():
    userId = request.args.get('userId')
    avgUp = request.args.get('avgUp')
    avgCm = request.args.get('avgCm')
    avgWb = request.args.get('avgWb')
    print(userId, avgUp, avgCm, avgWb)
    crawler = crawl.Crawl(userId, startDate, endDate)
    crawler.read_json()
    crawler.crawl()
    return "1"


@app.route('/weibo/crawlRcmd', methods=['GET'])
def rcmd():
    userId = request.args.get('userId')
    startDate = request.args.get('startDate')
    endDate = request.args.get('endDate')
    print(userId)
    crawler = crawl.Crawl(userId, startDate, endDate)
    crawler.read_json()
    crawler.crawl()
    return "1"


if __name__ == "__main__":
    app.run(host="0.0.0.0")
