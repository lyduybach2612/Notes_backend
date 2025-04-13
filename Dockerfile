FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-slim
LABEL maintainer="lyduybach900@gmail.com"
WORKDIR /app

COPY --from=build /app/target/notes-0.0.1-SNAPSHOT.jar /app/notes.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/notes.jar"]
