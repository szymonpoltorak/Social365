package razepl.dev.social365.notifications.consumer.data;

public record FriendshipEvent(String eventId, String timeStamp, String friendFullName,
                              String sourceProfileId, String targetProfileId) {
}
