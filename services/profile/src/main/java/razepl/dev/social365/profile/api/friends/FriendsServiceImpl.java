package razepl.dev.social365.profile.api.friends;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.interfaces.FriendsService;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendsServiceImpl implements FriendsService {

    private final ProfileRepository profileRepository;

    @Override
    public final List<FriendResponse> getFriends(String profileId, int page, int size) {
        return null;
    }

    @Override
    public final List<FriendResponse> getFriendRequests(String profileId, int page, int size) {
        return null;
    }

    @Override
    public final List<FriendResponse> getFriendSuggestions(String profileId, int page, int size) {
        return null;
    }

    @Override
    public final FriendResponse removeUserFromFriends(String profileId, String friendId) {
        return null;
    }

    @Override
    public final FriendResponse addUserToFriends(String profileId, String friendId) {
        return null;
    }
}
