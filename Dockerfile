FROM openjdk:17-alpine

EXPOSE 8282

COPY ./target/device-service-*.jar device-service.jar

ENTRYPOINT ["java", "-jar", "device-service.jar"]