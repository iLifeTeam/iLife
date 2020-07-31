from flask import Flask, request
import os
from flask_cors import CORS

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
    except TypeError:
        print("TypeError")
        return "TypeError"


if __name__ == "__main__":
    app.run(host="0.0.0.0")
