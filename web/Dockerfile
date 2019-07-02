FROM node:10
WORKDIR /app
COPY web ./
RUN npm install
EXPOSE 3000
CMD ["REACT_APP_PRODUCTION_API_URL=http://social-server:8080/api/v1 npm", "start"]
