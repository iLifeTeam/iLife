#/bin/bash
docker stop zhihu-service && docker rm zhihu-service
docker stop zhihu-crawler && docker rm zhihu-crawler
mvn clean package -Dmaven.test.skip=true
docker build . -t ilife2020/zhihu-service
docker run -p 8094:8090 --name=zhihu-service --network backend_default -d ilife2020/zhihu-service
docker run -p 8103:8103 --name=zhihu-crawler --network backend_default -d ilife2020/zhihu-python-crawler