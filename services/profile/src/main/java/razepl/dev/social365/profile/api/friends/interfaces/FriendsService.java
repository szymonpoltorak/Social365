package razepl.dev.social365.profile.api.friends.interfaces;

import razepl.dev.social365.profile.api.friends.data.FriendResponse;

import java.util.List;

public interface FriendsService {

    List<FriendResponse> getFriends(String profileId, int page, int size);

    List<FriendResponse> getFriendRequests(String profileId, int page, int size);

    List<FriendResponse> getFriendSuggestions(String profileId, int page, int size);

    FriendResponse removeUserFromFriends(String profileId, String friendId);

    FriendResponse addUserToFriends(String profileId, String friendId);

}
