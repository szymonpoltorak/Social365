FROM social:build AS build

WORKDIR /notifications

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle build -x test

RUN mv build/libs/notifications-service-0.0.1-SNAPSHOT.jar notifications-service.jar

FROM social:prod

WORKDIR /notifications

COPY --from=build /notifications/notifications-service.jar .

EXPOSE 8080

CMD ["java", "-jar", "notifications-service.jar"]
