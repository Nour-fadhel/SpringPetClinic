# Étape 1 : build Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY . .
RUN mvn -B -q package -DskipTests

# Étape 2 : image d'exécution
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/spring-petclinic-*.jar app.jar

EXPOSE 8085
ENTRYPOINT ["java","-jar","app.jar"]
