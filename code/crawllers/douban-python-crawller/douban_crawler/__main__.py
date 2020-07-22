import os
import sys

from absl import app
from douban_crawler.crawler import main

sys.path.append(os.path.abspath(os.path.dirname(os.getcwd())))
main()
