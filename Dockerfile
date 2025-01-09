FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/template-app-0.0.2.jar template-app-0.0.2.jar
EXPOSE 9000

ENTRYPOINT ["java","-jar","template-app-0.0.2.jar"]