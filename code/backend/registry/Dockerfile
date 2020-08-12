FROM maven:3-openjdk-14

# RUN apt update && apt install --yes maven
WORKDIR /app

VOLUME tmp

ADD pom.xml .

RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

ADD . .
RUN mvn clean package -Dmaven.test.skip=true
ENTRYPOINT ["java","-jar","./target/registry-0.0.1-SNAPSHOT.jar"]