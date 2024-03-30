package razepl.dev.social365.profile.api.friends.data;

import lombok.Builder;

@Builder
public record FriendSuggestionResponse(String profileId, String fullName, int numOfMutualFriends,
                                       String profilePictureUrl) {
}
