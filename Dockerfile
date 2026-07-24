# ==============================================================================
# STAGE 1: The Builder (Extracts the layers)
# ==============================================================================
FROM eclipse-temurin:21-jre-alpine AS builder
WORKDIR /builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=tools -jar app.jar extract --layers --destination extracted

# ==============================================================================
# STAGE 2: The Production Image
# ==============================================================================
FROM eclipse-temurin:21-jre-alpine

RUN apk upgrade --no-cache

LABEL org.opencontainers.image.authors="Mohamed" \
      org.opencontainers.image.description="Service d'envoi de notifications et emails pour ChangeTrack" \
      org.opencontainers.image.title="ChangeTrack Notification Service"

RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

COPY --from=builder --chown=spring:spring /builder/extracted/dependencies/ ./
COPY --from=builder --chown=spring:spring /builder/extracted/spring-boot-loader/ ./
COPY --from=builder --chown=spring:spring /builder/extracted/snapshot-dependencies/ ./
COPY --from=builder --chown=spring:spring /builder/extracted/application/ ./

EXPOSE 8080
USER spring:spring
ENTRYPOINT ["java", "-jar", "app.jar"]