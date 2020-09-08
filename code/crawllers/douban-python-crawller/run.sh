docker stop douban-crawler
docker rm douban-crawler
docker build . -t ilife2020/douban-crawler
docker run -d -p 8484:5000 --name douban-crawler ilife2020/douban-crawler
