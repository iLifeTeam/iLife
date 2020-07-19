from flask import Flask, url_for, request, render_template
from . import crawl
import os
app = Flask(__name__)


@app.route('/weibo/crawlWeibo', methods=['POST'])
def login():
    if request.method == 'POST':
        if 'user' in request.json:
            return 'Admin login successfully!'
        else:
            return 'No such user!'
    title = request.args.get('title', 'Default')
    return render_template('login.html', title=title)


if __name__ == "__main__":
    app.run(debug=True)
