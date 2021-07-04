FROM adoptopenjdk/openjdk11:jdk-11.0.2.9-slim
WORKDIR /
ENV PORT 8080
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
CMD java -jar -Dspring.profiles.active=prod app.jar