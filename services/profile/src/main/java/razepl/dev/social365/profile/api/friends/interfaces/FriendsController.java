package razepl.dev.social365.profile.api.friends.interfaces;

import org.springframework.data.domain.Page;
import razepl.dev.social365.profile.api.friends.data.FriendFeedResponse;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;
import razepl.dev.social365.profile.config.User;

public interface FriendsController {

    Page<FriendResponse> getFriends(User user, int pageNumber, int pageSize);

    Page<FriendResponse> getFriendsByPattern(User user, String pattern, int pageNumber, int pageSize);

    Page<FriendFeedResponse> getFriendsFeedOptions(User user, int pageNumber, int pageSize);

    Page<String> getFollowedProfileIds(User user, int pageNumber);

    Page<FriendSuggestionResponse> getFriendRequests(User user, int pageNumber, int pageSize);

    Page<FriendSuggestionResponse> getFriendSuggestions(User user, int pageNumber, int pageSize);

    FriendResponse removeUserFromFriends(User user, String friendId);

    FriendResponse addUserToFriends(User user, String friendId);

    FriendResponse addProfileToFollowed(User user, String friendId);

    FriendResponse removeProfileFromFollowed(User user, String toFollowId);

    FriendResponse sendFriendRequest(User user, String friendId);

    FriendResponse acceptFriendRequest(User user, String friendId);

    FriendResponse declineFriendRequest(User user, String friendId);

}
