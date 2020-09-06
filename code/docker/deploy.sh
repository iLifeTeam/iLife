#!/bin/sh

docker stack rm ilife
docker-compose pull -f config-eureka-zuul.yml
docker-compose pull -f services.yml

docker stack deploy --compose-file=config-eureka-zuul.yml ilife
docker stack deploy --compose-file=services.yml ilife
