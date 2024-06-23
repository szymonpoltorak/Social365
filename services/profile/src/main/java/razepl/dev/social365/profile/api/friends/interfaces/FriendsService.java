package razepl.dev.social365.profile.api.friends.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import razepl.dev.social365.profile.api.friends.data.FriendFeedResponse;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;

public interface FriendsService {

    Page<FriendResponse> getFriends(String profileId, Pageable pageable);

    Page<FriendResponse> getFriendsByPattern(String profileId, String pattern, Pageable pageable);

    Page<FriendFeedResponse> getFriendsFeedOptions(String profileId, Pageable pageable);

    Page<String> getFollowedProfileIds(String profileId, int pageNumber);

    Page<FriendSuggestionResponse> getFriendRequests(String profileId, Pageable pageable);

    Page<FriendSuggestionResponse> getFriendSuggestions(String profileId, Pageable pageable);

    FriendResponse removeUserFromFriends(String profileId, String friendId);

    FriendResponse addUserToFriends(String profileId, String friendId);

    FriendResponse addProfileToFollowed(String profileId, String friendId);

    FriendResponse removeProfileFromFollowed(String profileId, String toFollowId);

    FriendResponse sendFriendRequest(String profileId, String friendId);

    FriendResponse acceptFriendRequest(String profileId, String friendId);

    FriendResponse declineFriendRequest(String profileId, String friendId);

}
