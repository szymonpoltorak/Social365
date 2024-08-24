package razepl.dev.social365.profile.api.friends.interfaces;

import org.springframework.data.domain.Pageable;
import razepl.dev.social365.profile.api.friends.data.FriendFeedResponse;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;
import razepl.dev.social365.profile.config.User;
import razepl.dev.social365.profile.utils.pagination.SocialPage;

public interface FriendsService {

    SocialPage<FriendResponse> getFriends(User user, Pageable pageable);

    SocialPage<FriendResponse> getFriendsByPattern(User user, String pattern, Pageable pageable);

    SocialPage<FriendFeedResponse> getFriendsFeedOptions(String profileId, Pageable pageable);

    SocialPage<String> getFollowedProfileIds(User user, int pageNumber);

    SocialPage<FriendSuggestionResponse> getFriendRequests(User user, Pageable pageable);

    SocialPage<FriendSuggestionResponse> getFriendSuggestions(User user, Pageable pageable);

    FriendResponse removeUserFromFriends(User user, String friendId);

    FriendResponse addUserToFriends(User user, String friendId);

    FriendResponse addProfileToFollowed(User user, String friendId);

    FriendResponse removeProfileFromFollowed(User user, String toFollowId);

    FriendResponse sendFriendRequest(User user, String friendId);

    FriendResponse acceptFriendRequest(User user, String friendId);

    FriendResponse declineFriendRequest(User user, String friendId);

}
