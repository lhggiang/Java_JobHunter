# Stage 1: Build the application
FROM gradle:8.7-jdk17 AS build
COPY --chown=gradle:gradle . /hoanggiang/jobhunter
WORKDIR /hoanggiang/jobhunter

#skip task: test
RUN gradle clean build -x test --no-daemon

# Stage 2: Run the application
FROM openjdk:17-slim
EXPOSE 8080
COPY --from=build /hoanggiang/jobhunter/build/libs/*.jar /hoanggiang/spring-boot-job-hunter.jar
ENTRYPOINT ["java", "-jar", "/hoanggiang/spring-boot-job-hunter.jar"]