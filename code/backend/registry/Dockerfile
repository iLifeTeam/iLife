## build stage
FROM maven:3-openjdk-14

WORKDIR /app
VOLUME tmp

ADD pom.xml .
RUN mvn dependency:go-offline
# RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

ADD . .
RUN mvn clean package -Dmaven.test.skip=true

## runtime stage
FROM adoptopenjdk/openjdk14:alpine
WORKDIR /app
COPY --from=0 /app/target/gateway-0.0.1-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java","-jar","./app.jar"]