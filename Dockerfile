FROM maven:3.6.3-openjdk-11-slim as builder
WORKDIR /sabjicart
COPY . /sabjicart/
RUN mvn package

FROM adoptopenjdk/openjdk11:jre-11.0.8_10-alpine
COPY --from=builder /sabjicart/service/target/*.jar /sabjicart-service.jar

CMD ["java", "-jar", "/sabjicart-service.jar"]
