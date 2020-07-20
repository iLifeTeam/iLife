FROM python:3.8-slim

MAINTAINER Yanggen zhull991122@sjtu.edu.cn

WORKDIR /code

COPY requirements.txt /code

RUN pip install --no-cache-dir -r requirements.txt

ADD . /code

CMD ["ls"]

CMD ["python3","/code/restful.py","&"]
