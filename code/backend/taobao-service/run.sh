#/bin/bash
docker stop taobao-service && docker rm taobao-service
mvn clean package -Dmaven.test.skip=true
docker build . -t ilife2020/tb-service
docker run -p 8095:8090 --name=taobao-service --network backend_default -d ilife2020/tb-service