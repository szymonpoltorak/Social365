FROM social:build AS build

WORKDIR /data-init

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle build -x test

RUN mv build/libs/data-init-0.0.1-SNAPSHOT.jar data-init.jar

FROM social:prod

WORKDIR /data-init

COPY --from=build /data-init/data-init.jar .

EXPOSE 8080

CMD ["java", "-jar", "data-init.jar"]
