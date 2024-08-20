FROM alpine:3.19.1 AS build

RUN apk update --no-cache && apk upgrade --no-cache && apk add --no-cache gradle

WORKDIR /auth

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle build -x test

RUN mv build/libs/auth-0.0.1-SNAPSHOT.jar auth.jar

FROM alpine:3.19.1

WORKDIR /auth

RUN apk update --no-cache && apk upgrade --no-cache && apk add --no-cache openjdk17-jre curl

COPY --from=build /auth/auth.jar .

EXPOSE 8080

CMD ["java", "-jar", "auth.jar"]
