FROM social:build AS build

WORKDIR /api-gateway

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle build -x test

RUN mv build/libs/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

FROM social:prod

WORKDIR /api-gateway

COPY --from=build /api-gateway/api-gateway.jar .

EXPOSE 8080

CMD ["java", "-jar", "api-gateway.jar"]
