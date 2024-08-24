FROM social:build AS build

WORKDIR /posts-comments

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle build -x test

RUN mv build/libs/posts-comments-0.0.1-SNAPSHOT.jar posts-comments.jar

FROM social:prod

WORKDIR /posts-comments

COPY --from=build /posts-comments/posts-comments.jar .

EXPOSE 8080

CMD ["java", "-jar", "posts-comments.jar"]
