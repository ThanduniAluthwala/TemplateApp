FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y curl
MAINTAINER Qbitum
EXPOSE 8088
COPY /target/*.jar /app/application.jar

ENTRYPOINT ["java","-jar","/app/application.jar"]