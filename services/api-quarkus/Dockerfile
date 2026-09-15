# Estágio 1: Build da aplicação Quarkus
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /code

# Copia as dependências e o código-fonte
COPY pom.xml .
COPY src ./src

# Compila o projeto utilizando o Maven da imagem
RUN mvn package -DskipTests

# Estágio 2: Imagem final de execução
FROM registry.access.redhat.com/ubi9/openjdk-21-runtime:1.18
WORKDIR /deployments

COPY --from=build /code/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build /code/target/quarkus-app/*.jar /deployments/
COPY --from=build /code/target/quarkus-app/app/ /deployments/app/
COPY --from=build /code/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

CMD ["java", "-jar", "/deployments/quarkus-run.jar"]