FROM openjdk:11

COPY target/user-data-service-0.0.1-SNAPSHOT.jar /user-data-service.jar

CMD ["java", "-jar", "/user-data-service.jar"]