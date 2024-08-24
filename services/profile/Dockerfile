FROM social:build AS build

WORKDIR /profile-service

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle build -x test

RUN mv build/libs/profile-0.0.1-SNAPSHOT.jar profile-service.jar

FROM social:prod

WORKDIR /profile-service

COPY --from=build /profile-service/profile-service.jar .

EXPOSE 8080

CMD ["java", "-jar", "profile-service.jar"]
