FROM java:8u111

MAINTAINER zhull991122@sjtu.edu.cn

VOLUME /tmp

COPY *.jar app.jar

EXPOSE 80

ENTRYPOINT ["java","-jar","/app.jar"]

# Ubuntu 时区
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime