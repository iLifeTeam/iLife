FROM python:3.7-slim-stretch

MAINTAINER Yanggen zhull991122@sjtu.edu.cn

WORKDIR /code

COPY . /code

RUN pip install -i https://pypi.tuna.tsinghua.edu.cn/simple --no-cache-dir -r requirements.txt

CMD ["python3","restful.py"]
