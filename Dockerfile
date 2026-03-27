FROM openjdk:17-ea-17-jdk-slim-buster
LABEL maintainer="Souleymane Sy <sysouleymanesy259@gmail.com>"
COPY target/ms-produits-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8081
