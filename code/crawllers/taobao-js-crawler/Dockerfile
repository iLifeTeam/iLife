FROM node:12

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY ./ ./
EXPOSE 8101

CMD [ "node", "server.js" ]