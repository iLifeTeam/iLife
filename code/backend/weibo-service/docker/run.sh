docker stop weibo-service
docker rm weibo-service
docker build . -t ilife2020/weibo-service
docker run -p 8787:8081 -d --name weibo-service ilife2020/weibo-service
