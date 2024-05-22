FROM alpine:3.19.1 AS build

RUN apk update --no-cache && apk upgrade --no-cache && apk add --no-cache gradle

WORKDIR /data-init

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle build -x test

RUN mv build/libs/data-init-0.0.1-SNAPSHOT.jar data-init.jar

FROM alpine:3.19.1

WORKDIR /data-init

RUN apk update --no-cache && apk upgrade --no-cache && apk add --no-cache openjdk17-jre curl

COPY --from=build /data-init/data-init.jar .

EXPOSE 8080

CMD ["java", "-jar", "data-init.jar"]
