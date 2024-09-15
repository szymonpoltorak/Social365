FROM social:build AS build

WORKDIR /eureka-server

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle build -x test

RUN mv build/libs/eureka-registry-0.0.1-SNAPSHOT.jar eureka-registry.jar

FROM social:prod

WORKDIR /eureka-server

COPY --from=build /eureka-server/eureka-registry.jar .

EXPOSE 8080

CMD ["java", "-jar", "eureka-registry.jar"]
