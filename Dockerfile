# Stage 1 : Build
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY mvnw  .mvn/ pom.xml ./
RUN ./mvnw dependency:go-offline -B 
COPY src src
RUN ./mvnw pakcage -DskipTests -B 

# Stage 2 : Extract Layers 
FROM eclipse-temurin:21-jdk AS extractor
WORKDIR /app 
COPY --from=builder /app/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract 

# Stage 3 : Runtime 
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=extractor /app/dependencies/ ./
COPY --from=extractor /app/spring-boot-loader/ ./
COPY --from=extractor /app/application/ ./ 
EXPOSE 9011
ENTRYPOINT [ "java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "org.springframework.boot.loader.launch.JarLauncher" ]
