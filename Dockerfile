FROM maven:3.3.2-openjdk-17 as builder

LABEL org.opencontainers.image.title="OEPlan"
LABEL org.opencontainers.image.url="https://oeplan-api.theproject.id"
LABEL org.opencontainers.image.source="https://github.com/oetechbr/oeplan-backend"
LABEL org.opencontainers.image.description="Back-end do Projeto OEPlan - Sistema para Gerenciamento de Atividades Docentes"

LABEL maintainer="Lucas Josino <contact@lucasjosino.com>"
LABEL git="https://github/oetechbr/oeplan-backend"
LABEL website="https://oeplan-api.theproject.id"

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

FROM openjdk:17-jre-slim

COPY --from=builder /app/target/*.jar oeplan.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/oeplan.jar"]
