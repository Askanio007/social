FROM openjdk:8-jdk-alpine
COPY core/target/server-*.jar server.jar
CMD java -jar -Dspring.profiles.active=prod server.jar
