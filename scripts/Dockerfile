FROM ubuntu:latest as build

ENV LIBS_LOCATION="/usr/opt"
ENV GRAALVM_TAR="graalvm-community-jdk-17.0.9_linux-x64_bin.tar.gz"
ENV GRADLE_ZIP="gradle-8.5-bin.zip"
ENV GRADLE_HOME="${LIBS_LOCATION}/gradle-8.5"
ENV GRAALVM_HOME="${LIBS_LOCATION}/graalvm-community-openjdk-17.0.9+9.1"
ENV PATH="${PATH}:${GRAALVM_HOME}/bin:${GRADLE_HOME}/bin"
ENV JAVA_HOME="${GRAALVM_HOME}"

WORKDIR ${LIBS_LOCATION}

RUN apt update -y && apt upgrade -y && apt install -y curl

RUN curl -L --fail "https://github.com/graalvm/graalvm-ce-builds/releases/download/jdk-17.0.9/${GRAALVM_TAR}" -o "${GRAALVM_TAR}"

RUN curl -L --fail "https://services.gradle.org/distributions/${GRADLE_ZIP}" -o "${GRADLE_ZIP}"

RUN apt install -y unzip gcc binutils zlib1g-dev

RUN tar -xzvf "${GRAALVM_TAR}"

RUN unzip "${GRADLE_ZIP}"
