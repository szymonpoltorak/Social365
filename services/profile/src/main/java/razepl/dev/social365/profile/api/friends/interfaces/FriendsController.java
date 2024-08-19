package razepl.dev.social365.profile.api.friends.interfaces;

import razepl.dev.social365.profile.api.friends.data.FriendFeedResponse;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;
import razepl.dev.social365.profile.config.User;
import razepl.dev.social365.profile.utils.pagination.SocialPage;

public interface FriendsController {

    SocialPage<FriendResponse> getFriends(User user, int pageNumber, int pageSize);

    SocialPage<FriendResponse> getFriendsByPattern(User user, String pattern, int pageNumber, int pageSize);

    SocialPage<FriendFeedResponse> getFriendsFeedOptions(String profileId, int pageNumber, int pageSize);

    SocialPage<String> getFollowedProfileIds(User user, int pageNumber);

    SocialPage<FriendSuggestionResponse> getFriendRequests(User user, int pageNumber, int pageSize);

    SocialPage<FriendSuggestionResponse> getFriendSuggestions(User user, int pageNumber, int pageSize);

    FriendResponse removeUserFromFriends(User user, String friendId);

    FriendResponse addUserToFriends(User user, String friendId);

    FriendResponse addProfileToFollowed(User user, String friendId);

    FriendResponse removeProfileFromFollowed(User user, String toFollowId);

    FriendResponse sendFriendRequest(User user, String friendId);

    FriendResponse acceptFriendRequest(User user, String friendId);

    FriendResponse declineFriendRequest(User user, String friendId);

}
