# build
FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app
COPY . .
RUN mvn install -DskipTests=true

# run
FROM amazoncorretto:17.0.10-alpine

RUN adduser -D orio-be

WORKDIR /run
COPY --from=build /app/target/oriobook-0.0.1-SNAPSHOT.jar /run/oriobook-0.0.1-SNAPSHOT.jar

ENV SPRING_ENV=prod

RUN chown -R orio-be: /run

USER orio-be

EXPOSE 8080

ENTRYPOINT ["java","-jar", "/run/oriobook-0.0.1-SNAPSHOT.jar"]
