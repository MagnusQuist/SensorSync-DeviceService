FROM openjdk:17-alpine

EXPOSE 8282

COPY ./target/device-service-*.jar device-service.jar

RUN apk --no-cache add curl

ENTRYPOINT ["java", "-jar", "device-service.jar"]