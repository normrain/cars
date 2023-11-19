FROM gradle:8.3.0-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src/src/main/frontend
RUN apk add --update nodejs-current npm
RUN npm install -g @angular/cli@16.2.10
RUN npm install
RUN ng update
RUN npm update

WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17-jdk-alpine
EXPOSE 8080
RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/hometask.jar /app/spring-boot-application.jar


CMD ["java", "-jar", "/app/spring-boot-application.jar"]

EXPOSE 8080:8080