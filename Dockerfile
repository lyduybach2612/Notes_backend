FROM maven:3.8.3-openjdk-17
LABEL maintainer="lyduybach900@gmail.com"
WORKDIR /app
COPY target/notes-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "/app/notes-0.0.1-SNAPSHOT.jar"]