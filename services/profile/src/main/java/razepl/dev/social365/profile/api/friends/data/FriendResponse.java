package razepl.dev.social365.profile.api.friends.data;

import lombok.Builder;

@Builder
public record FriendResponse(String fullName, String profileId, String profilePictureUrl,
                             int numOfMutualFriends, boolean isFollowed) {
}
