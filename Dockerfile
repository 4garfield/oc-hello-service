FROM openjdk:11-jre-slim

COPY target/oc-hello-service-*.jar /tmp/oc-hello-service.jar
ENTRYPOINT ["java","-jar","/tmp/oc-hello-service.jar"]
