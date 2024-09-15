FROM social:build AS build

WORKDIR /home/notifications-gateway

COPY build.gradle .

COPY settings.gradle .

COPY src src/

RUN gradle build -x test

RUN mv build/libs/notifications-gateway-0.0.1-SNAPSHOT.jar notifications-gateway.jar

FROM social:prod

WORKDIR /home/notifications-gateway

COPY --from=build /home/notifications-gateway/notifications-gateway.jar .

EXPOSE 8080

CMD ["java", "-jar", "notifications-gateway.jar"]
