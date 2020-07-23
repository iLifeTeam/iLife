from python:3.8-slim

ADD requirements.txt /app/
WORKDIR /app
RUN pip install -r requirements.txt

ADD . .
RUN chmod 777 docker-entrypoint.sh \
    && ln -s docker-entrypoint.sh /
EXPOSE 7001
ENTRYPOINT ["/app/docker-entrypoint.sh"]