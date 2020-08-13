#!/bin/sh
docker stop config-server
docker rm config-server
docker build . -t ilife2020/config-server
docker run -p 8888:8888 -d --name=config-server ilife2020/config-server