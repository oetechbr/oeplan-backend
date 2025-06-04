FROM maven:3.9.9-amazoncorretto-17-alpine AS build

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

FROM amazoncorretto:17-alpine-jdk

COPY --from=builder /app/target/*.jar oeplan.jar

EXPOSE 8000

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/oeplan.jar"]
