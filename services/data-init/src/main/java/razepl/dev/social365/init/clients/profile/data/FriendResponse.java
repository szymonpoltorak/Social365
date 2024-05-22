package razepl.dev.social365.init.clients.profile.data;

import lombok.Builder;

@Builder
public record FriendResponse(String fullName, String profileId, String profilePictureUrl,
                             int numOfMutualFriends, boolean isFollowed) {
}
