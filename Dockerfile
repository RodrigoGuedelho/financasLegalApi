FROM openjdk:11

WORKDIR /app

COPY target/financasLegalApi-0.0.1-SNAPSHOT.jar /app/financeiroLegal.jar
COPY  /src/main/webapp/ /app/public/
ENTRYPOINT ["java", "-jar", "financeiroLegal.jar"]