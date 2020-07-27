import os
import sys
import logging
from douban_crawler.crawler import main

logging.basicConfig(level=logging.INFO,
                    format='%(asctime)s - %(levelname)s: %(message)s')
if len(sys.argv) <= 3:
    logging.info("Wrong Parameters")
    exit(1)
if not sys.argv[2] in ['movie', 'book', 'user']:
    logging.info("Wrong Request Type")
    exit(1)
sys.path.append(os.path.abspath(os.path.dirname(os.getcwd())))
main(sys.argv[1], sys.argv[2], sys.argv[3])
