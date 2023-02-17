FROM openjdk:11

COPY target/oc-hello-service-*.jar /tmp/oc-hello-service.jar
ENTRYPOINT ["java","-jar","/tmp/oc-hello-service.jar"]
