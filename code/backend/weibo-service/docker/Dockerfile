FROM adoptopenjdk/openjdk8:alpine

MAINTAINER zhull991122@sjtu.edu.cn

RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories \
    && apk update \
    && apk upgrade \
    && apk add --no-cache bash \
    && apk add --no-cache python3

VOLUME /tmp

COPY *.jar app.jar

EXPOSE 80

ENTRYPOINT ["java","-jar","/app.jar"]