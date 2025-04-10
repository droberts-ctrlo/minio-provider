FROM maven:3-amazoncorretto-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
