FROM openjdk:8-jdk-alpine
COPY core/target/server-*.jar server.jar
COPY camera_200.png camera_200.png
COPY photo photo
CMD java -jar -Dspring.profiles.active=prod server.jar
