FROM openjdk:17-jdk-slim

COPY build/libs/internal-0.0.1-SNAPSHOT.jar internal.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "internal.jar"]