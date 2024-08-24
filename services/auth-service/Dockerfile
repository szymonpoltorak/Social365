FROM social:build AS build

WORKDIR /auth

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle build -x test

RUN mv build/libs/auth-0.0.1-SNAPSHOT.jar auth.jar

FROM social:prod

WORKDIR /auth

COPY --from=build /auth/auth.jar .

EXPOSE 8080

CMD ["java", "-jar", "auth.jar"]
