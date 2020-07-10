FROM python:3.7

WORKDIR /app
COPY ./requirements.txt /app/requirements.txt
COPY ./zhihu_crawller_service.py /app/zhihu_crawller_service.py
RUN pip install -r requirements.txt

ENTRYPOINT [ "python" ]
CMD [ "/app/zhihu_crawller_service.py" ]