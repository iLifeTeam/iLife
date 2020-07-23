from flask import Flask, request
import os

app = Flask(__name__)


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
    except TypeError:
        print("TypeError")
        return "TypeError"


if __name__ == "__main__":
    app.run(host="0.0.0.0")
