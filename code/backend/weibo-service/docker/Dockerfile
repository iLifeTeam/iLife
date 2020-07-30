FROM adoptopenjdk/openjdk8:alpine

MAINTAINER zhull991122@sjtu.edu.cn

VOLUME /tmp

RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories

COPY *.jar app.jar

EXPOSE 80

ENTRYPOINT ["java","-jar","/app.jar"]