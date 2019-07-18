FROM node:10
WORKDIR /app
COPY web ./
RUN npm install -g serve
EXPOSE 5000
CMD ["serve", "-s", "build"]
