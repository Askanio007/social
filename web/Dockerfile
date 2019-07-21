FROM node:10
WORKDIR /app
COPY web ./
RUN npm install
RUN npm install -g serve
RUN npm run-script build
CMD ["serve", "-s", "build"]
