FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/demo-app-0.0.2.jar demo-app-0.0.2.jar
EXPOSE 9000

ENTRYPOINT ["java","-jar","template-app-0.0.2.jar"]