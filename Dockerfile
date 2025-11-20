# Multi-stage Dockerfile for building and running the Spring Boot app in `demo/`
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Copy only pom and sources to leverage docker cache
COPY demo/pom.xml demo/
COPY demo/src demo/src

RUN mvn -f demo/pom.xml -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy jar from builder stage
COPY --from=builder /workspace/demo/target/demo-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_OPTS=""
CMD ["sh","-c","java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
