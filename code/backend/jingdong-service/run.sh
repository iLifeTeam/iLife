#/bin/bash
docker stop jingdong-service && docker rm jingdong-service
mvn clean package -Dmaven.test.skip=true
docker build . -t ilife2020/jd-service
docker run -p 8096:8090 --name=jingdong-service --network backend_default -d ilife2020/jd-service