from python:3.8-slim

WORKDIR /app

ADD requirements.txt .
RUN pip install -r requirements.txt

ADD . .
ADD docker-entrypoint.sh /app
RUN chmod 777 docker-entrypoint.sh \
    && ln -s docker-entrypoint.sh /
EXPOSE 8102

ENTRYPOINT ["/app/docker-entrypoint.sh"]