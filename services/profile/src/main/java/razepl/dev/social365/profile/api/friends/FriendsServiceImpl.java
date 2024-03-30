package razepl.dev.social365.profile.api.friends;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.friends.data.FriendData;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestion;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;
import razepl.dev.social365.profile.api.friends.interfaces.FriendsService;
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.exceptions.UsersAlreadyFriendsException;
import razepl.dev.social365.profile.exceptions.UsersAreNotFriendsException;
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

        Page<FriendData> friends = profileRepository.findFriendsByProfileId(profileId, pageable);

        log.info("Found {} friends for profile with id: {}", friends.getTotalElements(), profileId);

        return friends
                .stream()
                .parallel()
                .map(profileMapper::mapFriendDataToFriendResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public final Set<FriendSuggestionResponse> getFriendRequests(String profileId, int page, int size) {
        log.info("Getting friend requests for profile with id: {}", profileId);

        Pageable pageable = PageRequest.of(page, size);

        Page<FriendSuggestion> friendRequests = profileRepository
                .findFriendRequestsByProfileId(profileId, pageable);

        log.info("Found {} friend requests for profile with id: {}", friendRequests.getTotalElements(), profileId);

        return friendRequests
                .stream()
                .parallel()
                .map(profileMapper::mapFriendSuggestionToFriendSuggestionResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public final Set<FriendSuggestionResponse> getFriendSuggestions(String profileId, int page, int size) {
        log.info("Getting friend suggestions for profile with id: {}", profileId);

        Pageable pageable = PageRequest.of(page, size);

        Page<FriendSuggestion> friendSuggestions = profileRepository.findProfileSuggestions(profileId, pageable);

        log.info("Found {} friend suggestions for profile with id: {}", friendSuggestions.getTotalElements(), profileId);

        return friendSuggestions
                .stream()
                .parallel()
                .map(profileMapper::mapFriendSuggestionToFriendSuggestionResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public final FriendResponse removeUserFromFriends(String profileId, String friendId) {
        log.info("Removing friend with id: {} from profile with id: {}", friendId, profileId);

        Profile profile = profileRepository.findByProfileId(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        Profile friend = profileRepository.findByProfileId(friendId)
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Found profile and friend.");

        if (!profile.getFriends().contains(friend)) {
            throw new UsersAreNotFriendsException(profileId, friendId);
        }
        log.info("Removing friend...");

        profile.getFriends().remove(friend);

        profile = profileRepository.save(profile);

        return profileMapper.mapProfileToFriendResponse(profile, -1, false);
    }

    @Override
    public final FriendResponse addUserToFriends(String profileId, String friendId) {
        log.info("Adding friend with id: {} to profile with id: {}", friendId, profileId);

        Profile profile = profileRepository.findByProfileId(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        Profile friend = profileRepository.findByProfileId(friendId)
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Found profile and friend.");

        if (profile.getFriends().contains(friend)) {
            throw new UsersAlreadyFriendsException(profileId, friendId);
        }
        log.info("Adding friend...");

        profile = addFriend(profile, friend);

        addFriend(friend, profile);

        return profileMapper.mapProfileToFriendResponse(profile, -1, false);
    }

    @Override
    public final FriendResponse changeFollowStatus(String profileId, String friendId) {
        log.info("Changing follow status for profile with id: {} and friend with id: {}", profileId, friendId);

        Profile profile = profileRepository.findByProfileId(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        Profile friend = profileRepository.findByProfileId(friendId)
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Found profile and friend.");

        if (profile.getFriends().contains(friend)) {
            profile.getFollowers().remove(friend);
        } else {
            profile.getFollowers().add(friend);
        }
        profile = profileRepository.save(profile);

        return profileMapper.mapProfileToFriendResponse(profile, -1, false);
    }

    private Profile addFriend(Profile profile, Profile friend) {
        profile.getFriends().add(friend);
        profile.getFollowers().add(friend);

        return profileRepository.save(profile);
    }
}
