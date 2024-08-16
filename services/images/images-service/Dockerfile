FROM social-native:base as build

ENV EXECUTABLE_NAME="images-service"
ENV WORKING_DIR="/${EXECUTABLE_NAME}"

WORKDIR "${WORKING_DIR}"

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle nativeBuild -x test

FROM ubuntu:latest

WORKDIR "${WORKING_DIR}"

RUN apt -y update && apt -y upgrade

RUN apt install -y curl

COPY --from=build "${WORKING_DIR}/build/native" .

RUN chmod +x "./nativeCompile/${EXECUTABLE_NAME}"

CMD ["./nativeCompile/${EXECUTABLE_NAME}"]
