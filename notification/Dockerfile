FROM openjdk:17-jdk-slim

COPY build/libs/notification-0.0.1-SNAPSHOT.jar notification.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "notification.jar"]