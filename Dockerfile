FROM openjdk:17-slim as build
LABEL authors="kirleon"

WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src src

RUN chmod +x mvnw
RUN ./mvnw clean install

ARG JAR_FILE=target/*.jar
COPY $JAR_FILE serve-together.jar

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /serve-together.jar)

FROM openjdk:17-slim
VOLUME /tmp

ARG DEPENDENCY=/target/dependency

COPY --from=build $DEPENDENCY/BOOT-INF/lib /serve-together/lib
COPY --from=build ${DEPENDENCY}/META-INF /serve-together/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /serve-together

ENTRYPOINT ["java","-cp","serve-together:serve-together/lib/*","com.bsuiramt.servetogether"]
