FROM social:build AS build

WORKDIR /config-server

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle build -x test

RUN mv build/libs/config-server-0.0.1-SNAPSHOT.jar config-server.jar

FROM social:prod

WORKDIR /config-server

COPY --from=build /config-server/config-server.jar .

EXPOSE 8080

CMD ["java", "-jar", "config-server.jar"]
