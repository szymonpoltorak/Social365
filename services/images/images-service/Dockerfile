FROM social:build AS build

WORKDIR /images-service

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle build -x test

RUN mv build/libs/images-service-0.0.1-SNAPSHOT.jar images-service.jar

FROM social:prod

WORKDIR /images-service

COPY --from=build /images-service/images-service.jar .

EXPOSE 8080

CMD ["java", "-jar", "images-service.jar"]
