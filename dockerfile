FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY target/digital_queue.jar app.jar 
FROM eclipse-temurin:17-jre-alpine
COPY --from=builder /app/app.jar .
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
