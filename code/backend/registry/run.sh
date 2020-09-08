#!/bin/sh
docker stop registry
docker rm registry
docker build . -t ilife2020/registry
docker run -p 1001:8090 -d --name=registry ilife2020/registry