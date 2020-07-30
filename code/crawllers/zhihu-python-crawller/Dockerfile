FROM python:3.8-slim

ADD requirements.txt /app/
WORKDIR /app
RUN pip install -r requirements.txt

ADD *.py /app/
ADD *.proto /app/
ADD docker-entrypoint.sh /app
RUN chmod 777 docker-entrypoint.sh \
    && ln -s docker-entrypoint.sh /
EXPOSE 8103

ENTRYPOINT ["/app/docker-entrypoint.sh"]
# ENTRYPOINT [ "python", "zhihu_crawller_service.py" ]