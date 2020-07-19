FROM adoptopenjdk/openjdk8:alpine

MAINTAINER zhull991122@sjtu.edu.cn

COPY ./lxml-4.5.2-cp38-cp38-linux_x86_64.whl /

RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories \
    && apk update \
    && apk upgrade \
    && apk add --no-cache bash \
    && apk add --no-cache python3 \
    && apk add --no-cache py-pip \
    && pip3 install absl-py \
    && apk add libxml2 \
    && apk add libxslt-dev \
    && pip3 install requests \
    && pip3 install tqdm \
    && pip3 install pymysql\
    && pip3 install wheel\
    && pip3 install lxml-4.5.2-cp38-cp38-linux_x86_64.whl


VOLUME /tmp

COPY *.jar app.jar

EXPOSE 80

ENTRYPOINT ["java","-jar","/app.jar"]