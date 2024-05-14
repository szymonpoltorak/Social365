FROM alpine:3.19.1 AS build

RUN apk update --no-cache && apk upgrade --no-cache && apk add --no-cache gradle

WORKDIR /posts-comments

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle build -x test

RUN mv build/libs/posts-comments-0.0.1-SNAPSHOT.jar posts-comments.jar

FROM alpine:3.19.1

WORKDIR /posts-comments

RUN apk update --no-cache && apk upgrade --no-cache && apk add --no-cache openjdk17-jre curl

COPY --from=build /posts-comments/posts-comments.jar .

EXPOSE 8080

CMD ["java", "-jar", "posts-comments.jar"]
