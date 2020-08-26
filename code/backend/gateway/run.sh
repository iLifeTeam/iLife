#!/bin/sh
docker stop gateway
docker rm gateway
docker build . -t ilife2020/gateway
docker run -p 8000:8090 -d --name=gateway ilife2020/gateway