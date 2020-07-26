from flask import Flask, abort, request, jsonify

import jd as JingDong
import json

app = Flask(__name__)

test_nickname = "jd_6e032844c7c86"
img_path = "./images"


@app.route("/login", methods=['GET'])
def login():
    if not request.args or 'username' not in request.args:
        return abort(400)
    else:
        return JingDong.login(request.args["username"])


@app.route("/login/check", methods=['GET'])
def loginCheck():
    if not request.args or 'username' not in request.args:
        return abort(400)
    else:
        return JingDong.checkLogin(request.args["username"])


@app.route("/history", methods=['GET'])
def fetch():
    if not request.args or 'username' not in request.args:
        return abort(400)
    else:
        username = request.args["username"]
        if 'year' not in request.args:
            return jsonify(JingDong.fetch(username, range(2015, 2020)))
        print(request.args["year"])
        year = max(2015, int(request.args["year"]))
        years = range(year, 2021)
        # print(years)
        return jsonify(JingDong.fetch(username, years))


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8102, debug=True)
