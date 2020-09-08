from flask import Flask, request
import os, json
from flask_cors import CORS
from douban_crawler.crawler_rcmd import main

app = Flask(__name__)
cors = CORS(app, resources={r"/*": {"origins": ["http://49.234.125.131", "http://localhost:3000"]}})


@app.after_request
def after_request(response):
    print(response)
    response.headers.add('Access-Control-Allow-Headers', 'Content-Type,Authorization')
    response.headers.add('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE,OPTIONS')

    response.headers.add('Access-Control-Allow-Credentials', 'true')
    return response


@app.route('/douban/crawlMovie', methods=['GET'])
def login():
    try:
        userId = request.args.get('userId')
        _type = request.args.get('type')
        limit = request.args.get('limit')
        command = "python3 -m douban_crawler " + str(userId) + " " + _type + " " + str(limit)
        print(command)
        os.system(command)
        return "0"
    except AttributeError:
        print("AttributeError occurs!Please check the crawler status.")
        return "Error"


@app.route('/douban/crawlRcmd', methods=['POST'])
def recommendation():
    try:
        data = request.get_data()
        json_data = json.loads(data.decode("utf-8"))
        print(json_data)
        bookTagList = json_data.get('bookTagList')
        preAuthor = json_data.get('preAuthor')
        movieTagList = json_data.get('movieTagList')
        musicTag = json_data.get('preAuthor')
        gameTag = json_data.get('preAuthor')
        attitude = json_data.get('attitude')
        hashTag = json_data.get('hashTag')
        tag =main(bookTagList, preAuthor, movieTagList, musicTag, gameTag, attitude,hashTag)
        print(tag)
        return tag
    except AttributeError or TypeError:
        print("TypeError or AttributeError")
        return "Error"


if __name__ == "__main__":
    app.run(host="0.0.0.0")
