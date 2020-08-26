FROM adoptopenjdk/openjdk8:alpine

VOLUME /tmp
COPY ./target/taobao-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]