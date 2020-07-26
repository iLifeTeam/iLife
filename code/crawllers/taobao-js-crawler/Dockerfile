FROM node:12

WORKDIR /app

COPY package*.json ./
RUN npm install
RUN apt install libnss3-dev
COPY ./ ./
EXPOSE 8101

CMD [ "node", "server.js" ]