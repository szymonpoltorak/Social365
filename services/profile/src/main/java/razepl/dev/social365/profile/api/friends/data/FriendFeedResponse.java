package razepl.dev.social365.profile.api.friends.data;

import lombok.Builder;

@Builder
public record FriendFeedResponse(String profileId, String fullName, boolean isOnline,
                                 String profilePictureUrl, String username) {
}
