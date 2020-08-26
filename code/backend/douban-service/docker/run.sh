docker stop douban-service
docker rm douban-service
docker build . -t ilife2020/douban-service
docker run -p 8383:8080 -d --name douban-service ilife2020/douban-service
