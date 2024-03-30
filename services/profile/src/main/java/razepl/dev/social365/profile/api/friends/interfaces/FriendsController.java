package razepl.dev.social365.profile.api.friends.interfaces;

import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;

import java.util.Set;

public interface FriendsController {

    Set<FriendResponse> getFriends(String profileId, int page, int size);

    Set<FriendSuggestionResponse> getFriendRequests(String profileId, int page, int size);

    Set<FriendSuggestionResponse> getFriendSuggestions(String profileId, int page, int size);

    FriendResponse removeUserFromFriends(String profileId, String friendId);

    FriendResponse addUserToFriends(String profileId, String friendId);

    FriendResponse changeFollowStatus(String profileId, String friendId);

}
