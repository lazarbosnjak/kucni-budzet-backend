# Stage 1: Build the application
#FROM maven:3.8.4-openjdk-17 AS build
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
# RUN mvn clean install -X

# Stage 2: Run the application
FROM openjdk:17-alpine
ADD target/kucni-budzet.jar kucni-budzet.jar
EXPOSE 8080
CMD ["java", "-jar", "kucni-budzet.jar"]