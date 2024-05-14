package razepl.dev.social365.profile.api.friends.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;

public interface FriendsService {

    Page<FriendResponse> getFriends(String profileId, Pageable pageable);

    Page<FriendSuggestionResponse> getFriendRequests(String profileId, Pageable pageable);

    Page<FriendSuggestionResponse> getFriendSuggestions(String profileId, Pageable pageable);

    FriendResponse removeUserFromFriends(String profileId, String friendId);

    FriendResponse addUserToFriends(String profileId, String friendId);

    FriendResponse changeFollowStatus(String profileId, String friendId);

}
