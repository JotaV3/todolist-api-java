
# Step 1: Building the application
FROM maven:3.10-eclipse-temurin-25 AS build

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN chmod +x mvnw

RUN ./mvnw -B dependency:go-offline

COPY src/ src/

RUN ./mvnw -B clean package

# Step 2: Application execution
FROM eclipse-temurin:25-jre-ubi10-minimal

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
