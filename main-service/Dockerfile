FROM amazoncorretto:17.0.11-alpine3.19
COPY target/*.jar main_service.jar
EXPOSE ${SERVER_EXTERNAL_PORT_APPLICATION}
ENTRYPOINT ["java", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-jar","/main_service.jar"]