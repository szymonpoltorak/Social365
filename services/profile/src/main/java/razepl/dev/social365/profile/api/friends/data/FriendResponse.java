package razepl.dev.social365.profile.api.friends.data;

import lombok.Builder;

@Builder
public record FriendResponse(String fullName, String profileId, String profilePictureUrl, String username,
                             int numOfMutualFriends, boolean isFollowed) {
}
