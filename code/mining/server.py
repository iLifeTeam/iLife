import base64
import os

from flask import Flask, abort, request, jsonify
from wordCloud import *
from flask_cors import CORS

app = Flask(__name__)
cors = CORS(app, resources={r"/*": {"origins": ["http://49.234.125.131", "http://localhost:3000"]}})

test_uid = "552399318699515904"
img_path = "./images"


def createDirIfNotExists(path):
    if not os.path.exists(path):
        os.makedirs(path)


@app.after_request
def after_request(response):
  #response.headers.add('Access-Control-Allow-Origin', 'http://localhost:3000')
  response.headers.add('Access-Control-Allow-Headers', 'Content-Type,Authorization')
  response.headers.add('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE,OPTIONS')
  response.headers.add('Access-Control-Allow-Credentials', 'true')
  return response

@app.route("/word_cloud", methods=['GET'])
def word_cloud():
    if not request.args or 'uid' not in request.args:
        return abort(400)
    else:
        uid = request.args['uid']
        activity_type = ""
        if 'type' in request.args:
            activity_type = request.args['type']
        wc = generateWordCloud(uid, activity_type)
        path = img_path + "/" + uid + "_" + activity_type + ".png"
        wc.to_file(path)
        with open(path, "rb") as image:
            image_string = base64.b64encode(image.read())
        return image_string


if __name__ == '__main__':
    createDirIfNotExists(img_path)
    app.run(host='0.0.0.0', port=7001, debug=True)
