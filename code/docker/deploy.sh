#!/bin/sh

docker stack rm ilife
# docker-compose  -f config-eureka-zuul.yml pull
# docker-compose  -f services.yml pull
# docker-compose -f visual.yml pull
sleep 2
docker stack deploy --compose-file=config-eureka-zuul.yml ilife

docker stack deploy --compose-file=visual.yml ilife

docker stack deploy --compose-file=services.yml ilife
