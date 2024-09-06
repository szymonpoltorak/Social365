# Notifications Service

This service is responsible for sending notifications to users. It is a microservice that is part of the `Social365` app.

## Kafka Notifications Diagram

The following diagram shows what events are sent to the `notifications` topic in Kafka. The `notifications` topic is consumed by the `notifications` service.

![Kafka Notifications Diagram](diagrams/notifications-kafka.drawio.png)

## MongoDB Schema Diagram

The following diagram shows the schema for the `notifications` collection in MongoDB.

![MongoDB Schema Diagram](diagrams/notifications-mongodb.drawio.png)
