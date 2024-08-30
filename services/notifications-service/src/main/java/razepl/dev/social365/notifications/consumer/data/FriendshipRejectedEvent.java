package razepl.dev.social365.notifications.consumer.data;

public record FriendshipRejectedEvent(String eventId, String timeStamp, String sourceProfileId, String targetProfileId) {
}
