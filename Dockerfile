FROM adoptopenjdk/openjdk11:alpine-slim
WORKDIR /
ENV PORT 8080
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
CMD java -jar -Dspring.profiles.active=prod app.jar