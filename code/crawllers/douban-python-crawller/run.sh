docker stop douban-crawler
docker rm douban-crawler
docker build . -t ilife2020/douban-crawler
docker run -p 8484:5000 -d --name douban-crawler ilife2020/douban-crawler