package razepl.dev.social365.notifications.consumer;


public record NotificationEvent(String profileId, String eventId, String userProducerName) {
}
