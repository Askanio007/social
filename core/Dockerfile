FROM openjdk:8-jdk-alpine
COPY core/target/server-*.jar server.jar
COPY camera_200.png camera_200.png
RUN mkdir photo
RUN mkdir photo/big
RUN mkdir photo/mini
CMD java -jar -Dspring.profiles.active=prod server.jar
