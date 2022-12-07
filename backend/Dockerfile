FROM mcr.microsoft.com/openjdk/jdk:17-ubuntu
EXPOSE 8080
COPY build/libs/*.jar cryptocurrencies.jar
ENTRYPOINT ["java", "-jar", "/cryptocurrencies.jar"]