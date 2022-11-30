FROM openjdk:17
EXPOSE 80
EXPOSE 8080
COPY build/libs/*.jar cryptocurrencies.jar
ENTRYPOINT ["java", "-jar", "/cryptocurrencies.jar"]