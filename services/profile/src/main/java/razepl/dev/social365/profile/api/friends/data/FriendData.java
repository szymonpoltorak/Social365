package razepl.dev.social365.profile.api.friends.data;

import lombok.Builder;
import razepl.dev.social365.profile.nodes.profile.Profile;

@Builder
public record FriendData(FriendProfile profile, int mutualFriendsCount, boolean isFollowed) {
}
