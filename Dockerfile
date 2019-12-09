FROM openjdk:8
ADD target/wcms.jar wcms.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "wcms.jar"]