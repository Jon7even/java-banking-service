FROM amazoncorretto:17.0.11-alpine3.19
COPY target/*.jar main_service.jar
ENTRYPOINT ["java","-jar","/main_service.jar"]