package razepl.dev.social365.profile.api.friends;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;
import razepl.dev.social365.profile.api.friends.interfaces.FriendsService;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendsServiceImpl implements FriendsService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public final Set<FriendResponse> getFriends(String profileId, int page, int size) {
        log.info("Getting friends for profile with id: {}", profileId);

        Pageable pageable = PageRequest.of(page, size);

        Page<Profile> friends = profileRepository.findFriendsByProfileId(profileId, pageable);

        log.info("Found {} friends for profile with id: {}", friends.getTotalElements(), profileId);

        return friends
                .stream()
                .parallel()
                .map(friend -> {
                    int numOfMutualFriends = profileRepository
                            .countMutualFriends(profileId, friend.getProfileId());
                    boolean isFollowed = profileRepository
                            .isProfileFollowedByFriend(profileId, friend.getProfileId());

                    return profileMapper.mapProfileToFriendResponse(friend, numOfMutualFriends, isFollowed);

                })
                .collect(Collectors.toSet());
    }

    @Override
    public final Set<FriendSuggestionResponse> getFriendRequests(String profileId, int page, int size) {
        log.info("Getting friend requests for profile with id: {}", profileId);

        Pageable pageable = PageRequest.of(page, size);

        Page<Profile> friendRequests = profileRepository.findFriendRequestsByProfileId(profileId, pageable);

        log.info("Found {} friend requests for profile with id: {}", friendRequests.getTotalElements(), profileId);

        return friendRequests
                .stream()
                .parallel()
                .map(request -> {
                    int numOfMutualFriends = profileRepository
                            .countMutualFriends(profileId, request.getProfileId());

                    return profileMapper.mapProfileToFriendSuggestionResponse(request, numOfMutualFriends);
                })
                .collect(Collectors.toSet());
    }

    @Override
    public final Set<FriendSuggestionResponse> getFriendSuggestions(String profileId, int page, int size) {
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
