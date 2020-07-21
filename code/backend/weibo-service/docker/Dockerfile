FROM adoptopenjdk/openjdk8:alpine

MAINTAINER zhull991122@sjtu.edu.cn

VOLUME /tmp
sd
COPY *.jar app.jar

EXPOSE 80

ENTRYPOINT ["java","-jar","/app.jar"]