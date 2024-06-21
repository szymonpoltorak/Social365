package razepl.dev.social365.profile.api.friends;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.friends.data.FriendData;
import razepl.dev.social365.profile.api.friends.data.FriendFeedResponse;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestion;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;
import razepl.dev.social365.profile.api.friends.interfaces.FriendsService;
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.exceptions.UserAlreadyFollows;
import razepl.dev.social365.profile.exceptions.UserAlreadySendFriendRequestException;
import razepl.dev.social365.profile.exceptions.UserDidNotSendFriendRequestException;
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

    @Override
    public final Page<FriendResponse> getFriends(String profileId, Pageable pageable) {
        log.info("Getting friends for profile with id: {} with pageable : {}", profileId, pageable);

        Page<FriendData> friends = profileRepository.findFriendsByProfileId(profileId, pageable);

        return mapFriendDataPageToFriendResponse(friends, profileId);
    }

    @Override
    public final Page<FriendResponse> getFriendsByPattern(String profileId, String pattern, Pageable pageable) {
        log.info("Getting friends for profile with id: {} with pattern: {} and pageable : {}", profileId, pattern, pageable);

        Page<FriendData> friends = profileRepository.findFriendsByProfileIdAndPattern(profileId, pattern, pageable);

        return mapFriendDataPageToFriendResponse(friends, profileId);
    }

    @Override
    public final Page<FriendFeedResponse> getFriendsFeedOptions(String profileId, Pageable pageable) {
        log.info("Getting friends feed options for profileId: {} with pageable : {}", profileId, pageable);

        Page<Profile> friends = profileRepository.findOnlineFriendsByProfileId(profileId, pageable);

        log.info("Found online friends for feed : {}, for profileId: {}", friends.getNumberOfElements(), profileId);

        return friends.map(profileMapper::mapProfileToFriendFeedResponse);
    }

    @Override
    public final Page<FriendSuggestionResponse> getFriendRequests(String profileId, Pageable pageable) {
        log.info("Getting friend requests for profile with id: {}", profileId);

        Page<FriendSuggestion> friendRequests = profileRepository
                .findFriendRequestsByProfileId(profileId, pageable);

        log.info("Found {} friend requests for profile with id: {}", friendRequests.getNumberOfElements(), profileId);

        return friendRequests.map(profileMapper::mapFriendSuggestionToFriendSuggestionResponse);
    }

    @Override
    public final Page<FriendSuggestionResponse> getFriendSuggestions(String profileId, Pageable pageable) {
        log.info("Getting friend suggestions for profile with id: {}", profileId);

        Page<FriendSuggestion> friendSuggestions = profileRepository.findProfileSuggestions(profileId, pageable);

        log.info("Found {} friend suggestions for profile with id: {}", friendSuggestions.getNumberOfElements(), profileId);

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

        log.info("Removing follow relation...");

        profileRepository.deleteFollowsRelation(profileId, friendId);

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

        log.info("Found {} friends ids for profile with id: {}", friendsIds.getNumberOfElements(), profileId);

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

    @Override
    public final FriendResponse sendFriendRequest(String profileId, String friendId) {
        log.info("Sending friend request from profile with id: {} to user with id: {}", profileId, friendId);

        Profile profile = getProfileAndCheckFriendExistence(profileId, friendId);

        if (profileRepository.areUsersFriends(profileId, friendId)) {
            throw new UsersAlreadyFriendsException(profileId, friendId);
        }
        if (profileRepository.doesUserWantToBeFriendWith(profileId, friendId)) {
            throw new UserAlreadySendFriendRequestException(profileId, friendId);
        }
        log.info("Sending friend request. to user : {} ...", friendId);

        profileRepository.createWantsToBeFriendWithRelation(profileId, friendId);

        return profileMapper.mapProfileToFriendResponse(profile, -1, false);
    }

    @Override
    public final FriendResponse acceptFriendRequest(String profileId, String friendId) {
        log.info("Accepting friend request from profile with id: {} to user with id: {}", profileId, friendId);

        Profile profile = getProfileAndCheckFriendExistence(profileId, friendId);

        if (!profileRepository.doesUserWantToBeFriendWith(profileId, friendId)) {
            throw new UserDidNotSendFriendRequestException(profileId, friendId);
        }
        log.info("Accepting friend request. from user : {} ...", profileId);

        profileRepository.createFriendsWithRelation(profileId, friendId);
        profileRepository.deleteWantsToBeFriendWithRelation(profileId, friendId);

        return profileMapper.mapProfileToFriendResponse(profile, -1, false);
    }

    @Override
    public final FriendResponse declineFriendRequest(String profileId, String friendId) {
        log.info("Declining friend request from profile with id: {} to user with id: {}", profileId, friendId);

        Profile profile = getProfileAndCheckFriendExistence(profileId, friendId);

        if (!profileRepository.doesUserWantToBeFriendWith(profileId, friendId)) {
            throw new UserDidNotSendFriendRequestException(profileId, friendId);
        }
        log.info("Declining friend request. from user : {} ...", profileId);

        profileRepository.deleteWantsToBeFriendWithRelation(profileId, friendId);

        return profileMapper.mapProfileToFriendResponse(profile, -1, false);
    }

    private Page<FriendResponse> mapFriendDataPageToFriendResponse(Page<FriendData> friends, String profileId) {
        log.info("Found {} friends for profile with id: {}", friends.getNumberOfElements(), profileId);

        return friends.map(profileMapper::mapFriendDataToFriendResponse);
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
