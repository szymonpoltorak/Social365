package razepl.dev.social365.profile.api.friends.data;

import razepl.dev.social365.profile.nodes.profile.Profile;

public record FriendSuggestion(Profile profile, int mutualFriendsCount) {
}
