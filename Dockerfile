FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY Wallet_MIBDA2 /app/oracle_wallet
ENV TNS_ADMIN=/app/oracle_wallet
RUN mvn -q clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY Wallet_MIBDA2 /app/oracle_wallet
ENV TNS_ADMIN=/app/oracle_wallet
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
