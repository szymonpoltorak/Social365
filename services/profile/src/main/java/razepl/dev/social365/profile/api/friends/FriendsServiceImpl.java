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
import razepl.dev.social365.profile.exceptions.UserAlreadyFollows;
import razepl.dev.social365.profile.exceptions.UsersAlreadyFriendsException;
import razepl.dev.social365.profile.exceptions.UsersAreNotFriendsException;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendsServiceImpl implements FriendsService {

    private static final int PAGE_SIZE = 20;

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    //TODO: add sending friend requests, declining friend requests

    @Override
    public final Page<FriendResponse> getFriends(String profileId, Pageable pageable) {
        log.info("Getting friends for profile with id: {}", profileId);

        Page<FriendData> friends = profileRepository.findFriendsByProfileId(profileId, pageable);

        log.info("Found {} friends for profile with id: {}", friends.getTotalElements(), profileId);

        return friends.map(profileMapper::mapFriendDataToFriendResponse);
    }

    @Override
    public final Page<FriendSuggestionResponse> getFriendRequests(String profileId, Pageable pageable) {
        log.info("Getting friend requests for profile with id: {}", profileId);

        Page<FriendSuggestion> friendRequests = profileRepository
                .findFriendRequestsByProfileId(profileId, pageable);

        log.info("Found {} friend requests for profile with id: {}", friendRequests.getTotalElements(), profileId);

        return friendRequests.map(profileMapper::mapFriendSuggestionToFriendSuggestionResponse);
    }

    @Override
    public final Page<FriendSuggestionResponse> getFriendSuggestions(String profileId, Pageable pageable) {
        log.info("Getting friend suggestions for profile with id: {}", profileId);

        Page<FriendSuggestion> friendSuggestions = profileRepository.findProfileSuggestions(profileId, pageable);

        log.info("Found {} friend suggestions for profile with id: {}", friendSuggestions.getTotalElements(), profileId);

        return friendSuggestions.map(profileMapper::mapFriendSuggestionToFriendSuggestionResponse);
    }

    @Override
    public final FriendResponse removeUserFromFriends(String profileId, String friendId) {
        log.info("Removing friend with id: {} from profile with id: {}", friendId, profileId);

        Profile profile = getProfileAndCheckFriendExistence(profileId, friendId);

        if (!profileRepository.areUsersFriends(profileId, friendId)) {
            throw new UsersAreNotFriendsException(profileId, friendId);
        }
        log.info("Removing friend...");

        profileRepository.deleteFriendshipRelationship(profileId, friendId);
        profileRepository.deleteFriendshipRelationship(friendId, profileId);

        log.info("Removing follow relation...");

        profileRepository.deleteFollowsRelation(profileId, friendId);
        profileRepository.deleteFollowsRelation(friendId, profileId);

        return profileMapper.mapProfileToFriendResponse(profile, -1, false);
    }

    @Override
    public final FriendResponse addUserToFriends(String profileId, String friendId) {
        log.info("Adding friend with id: {} to profile with id: {}", friendId, profileId);

        Profile profile = getProfileAndCheckFriendExistence(profileId, friendId);

        if (profileRepository.areUsersFriends(profileId, friendId)) {
            throw new UsersAlreadyFriendsException(profileId, friendId);
        }
        log.info("Adding friendship relation...");

        profileRepository.createFriendsWithRelation(profileId, friendId);
        profileRepository.createFriendsWithRelation(friendId, profileId);

        log.info("Adding follow relation...");

        profileRepository.createFollowsRelation(profileId, friendId);
        profileRepository.createFollowsRelation(friendId, profileId);

        return profileMapper.mapProfileToFriendResponse(profile, -1, false);
    }

    @Override
    public final Page<String> getFollowedProfileIds(String profileId, int pageNumber) {
        log.info("Getting friends ids for profile with id: {}", profileId);

        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);

        Page<String> friendsIds = profileRepository.findFollowedIdsByProfileId(profileId, pageable);

        log.info("Found {} friends ids for profile with id: {}", friendsIds.getTotalElements(), profileId);

        return friendsIds;
    }

    @Override
    public final FriendResponse addProfileToFollowed(String profileId, String toFollowId) {
        log.info("Adding follow status for profile with id: {} and user with id: {}", profileId, toFollowId);

        Profile profile = getProfileAndCheckFriendExistence(profileId, toFollowId);

        if (profileRepository.doesUserFollowProfile(profileId, toFollowId)) {
            throw new UserAlreadyFollows(profileId, toFollowId);
        }
        log.info("Following user with id : {} ...", toFollowId);

        profileRepository.createFollowsRelation(profileId, toFollowId);

        return profileMapper.mapProfileToFriendResponse(profile, -1, false);
    }

    @Override
    public final FriendResponse removeProfileFromFollowed(String profileId, String toFollowId) {
        log.info("Removing follow status for profile with id: {} and user with id: {}", profileId, toFollowId);

        Profile profile = getProfileAndCheckFriendExistence(profileId, toFollowId);

        if (!profileRepository.doesUserFollowProfile(profileId, toFollowId)) {
            throw new UserAlreadyFollows(profileId, toFollowId);
        }
        log.info("Unfollowing user with id : {} ...", toFollowId);

        profileRepository.deleteFollowsRelation(profileId, toFollowId);

        return profileMapper.mapProfileToFriendResponse(profile, -1, false);
    }

    private Profile getProfileAndCheckFriendExistence(String profileId, String friendId) {
        Profile profile = profileRepository.findByProfileId(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        Profile friend = profileRepository.findByProfileId(friendId)
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Found profile: {} and friend {}.", profile, friend);

        return profile;
    }
}
