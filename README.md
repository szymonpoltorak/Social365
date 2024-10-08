# Social365

![Home banner](./screenshots/readme/home-banner.png)

Social Media application made with modern technologies. This is repository for my diploma project. Goal is to create an App in Microservice architecture.
Frontend is made with Angular 17 and Angular Material. Backend is made with Spring Boot 3. UI is full responsive and PWA ready.

## How to run it

I provided shell scripts written in `bin/sh` to run the app. They use `docker compose` to run the app but they are shorter than pasting whole command to console. All the scripts I written are listed below:

* `rdv` - run development configuration,
* `sdv` - stop all running dev containers, remove them and their images,
* `rpd` - run app in production configuration,
* `spd` - stop all running prod containers, remove them and their images,
* `rsa` - the script go generate rsa public and private keys for signing jwt.

You can configure which service you want to be in dev or prod config in `compose.yml` or `compose.dev.yml`.

## How this app looks like

![Feed page](./screenshots/browser/feed.png)

Every view that is now part of the application is inside `screenshots` directory in subdirectories:

* `browser` - for laptop browser size,
* `browser-pwa` - for browser resized to mobile size

## Technology stack

![Tech stack image](./screenshots/readme/tech-stack.png)

1. Frontend

* Angular 17
* Angular Material
* Angular PWA
* Tailwindcss

2. Backend

* Spring Boot 3
* Kafka
* Lombok
* Netflix Eureka
* Spring Data
* Spring Cloud Api Gateway
* Spring Cloud Config Server
* Spring Doc OpenApi

3. Databases

* Postgres SQL
* Cassandra
* Neo4j

4. Others

* Nginx
* Docker
* Docker Compose
* DrawIo

## Diagrams

### Main architecture

<p align="center">
    <img src="docs/Main_Diagram.drawio.png" align="center">
</p>

### Microservices

<p align="center">
    <img src="docs/Services.drawio.png" align="center">
</p>
