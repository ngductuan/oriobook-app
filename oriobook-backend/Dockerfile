### BUILD STAGE
FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml ./
# use cache when pom.xml is not changed
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn install -DskipTests=true

### RUN STAGE
FROM gcr.io/distroless/java17

# Set up a non-root user
USER 1000

WORKDIR /run
COPY --from=build /app/target/oriobook-0.0.1-SNAPSHOT.jar /run/oriobook-0.0.1-SNAPSHOT.jar

ENV SPRING_ENV=prod

RUN mkdir -p /logs && chmod 777 /logs

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/run/oriobook-0.0.1-SNAPSHOT.jar"]
