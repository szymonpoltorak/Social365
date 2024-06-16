package razepl.dev.social365.profile.api.friends.interfaces;

import org.springframework.data.domain.Page;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;

public interface FriendsController {

    Page<FriendResponse> getFriends(String profileId, int pageNumber, int pageSize);

    Page<String> getFollowedProfileIds(String profileId, int pageNumber);

    Page<FriendSuggestionResponse> getFriendRequests(String profileId, int pageNumber, int pageSize);

    Page<FriendSuggestionResponse> getFriendSuggestions(String profileId, int pageNumber, int pageSize);

    FriendResponse removeUserFromFriends(String profileId, String friendId);

    FriendResponse addUserToFriends(String profileId, String friendId);

    FriendResponse addProfileToFollowed(String profileId, String friendId);

    FriendResponse removeProfileFromFollowed(String profileId, String toFollowId);

    FriendResponse sendFriendRequest(String profileId, String friendId);

    FriendResponse acceptFriendRequest(String profileId, String friendId);

    FriendResponse declineFriendRequest(String profileId, String friendId);

}
