# ----------- Base Image with JDK 17 -----------
FROM eclipse-temurin:17-jdk-alpine

# ----------- Set working directory -----------
WORKDIR /app

# ----------- Copy built JAR file -----------
COPY target/pharmease-backend.jar app.jar

# ----------- Expose port 8080 -----------
EXPOSE 8080

# ----------- Run the JAR file -----------
ENTRYPOINT ["java", "-jar", "app.jar"]
