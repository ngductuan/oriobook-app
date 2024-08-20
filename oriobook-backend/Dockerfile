# build
FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml ./
COPY src/main/resources/application.properties src/main/resources/

# use cache when pom.xml is not changed
RUN mvn dependency:go-offline -B

# Sau đó mới copy toàn bộ mã nguồn
COPY src ./src

RUN mvn install -DskipTests=true

# run
FROM gcr.io/distroless/java17

RUN adduser -D orio-be

WORKDIR /run
COPY --from=build /app/target/oriobook-0.0.1-SNAPSHOT.jar /run/oriobook-0.0.1-SNAPSHOT.jar

ENV SPRING_ENV=prod

RUN chown -R orio-be: /run

USER orio-be

EXPOSE 8080

ENTRYPOINT ["java","-jar", "/run/oriobook-0.0.1-SNAPSHOT.jar"]
