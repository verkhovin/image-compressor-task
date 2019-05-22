FROM java:8
EXPOSE 8080
ADD /build/libs/imagecompressor-0.0.1-SNAPSHOT.jar imagecompressor.jar
ENTRYPOINT ["java", "-jar", "imagecompressor.jar"]

