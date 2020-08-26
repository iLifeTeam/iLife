#/bin/bash
docker stop tb-service && docker rm tb-service
mvn clean package -Dmaven.test.skip=true
docker build . -t ilife2020/tb-service
docker run -p 8095:8090 --name=tb-service -d ilife2020/tb-service