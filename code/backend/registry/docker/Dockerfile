FROM adoptopenjdk/openjdk14:alpine

#RUN apt install --yes maven
WORKDIR /app

VOLUME tmp
COPY . .
#COPY ./target/taobao-service-0.0.1-SNAPSHOT.jar app.jar
RUN mvn clean package -Dmaven.test.skip=true

ENTRYPOINT ["java","-cd doDjava.security.egd=file:/dev/./urandom","-jar","/./target/taobao-service-0.0.1-SNAPSHOT.jar"]