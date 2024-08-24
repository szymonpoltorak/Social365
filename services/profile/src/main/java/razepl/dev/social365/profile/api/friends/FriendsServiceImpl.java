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
import razepl.dev.social365.profile.config.User;
import razepl.dev.social365.profile.utils.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.utils.exceptions.UserAlreadyFollows;
import razepl.dev.social365.profile.utils.exceptions.UserAlreadySendFriendRequestException;
import razepl.dev.social365.profile.utils.exceptions.UserDidNotSendFriendRequestException;
import razepl.dev.social365.profile.utils.exceptions.UsersAlreadyFriendsException;
import razepl.dev.social365.profile.utils.exceptions.UsersAreNotFriendsException;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;
import razepl.dev.social365.profile.utils.pagination.SocialPage;
import razepl.dev.social365.profile.utils.pagination.SocialPageImpl;

import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendsServiceImpl implements FriendsService {

    private static final int PAGE_SIZE = 20;

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public final SocialPage<FriendResponse> getFriends(User user, Pageable pageable) {
        log.info("Getting friends for user : {} with pageable : {}", user, pageable);

        Page<FriendData> friends = profileRepository.findFriendsByProfileId(user.profileId(), pageable);

        return mapFriendDataPageToFriendResponse(friends, user.profileId());
    }

    @Override
    public final SocialPage<FriendResponse> getFriendsByPattern(User user, String pattern, Pageable pageable) {
        log.info("Getting friends for user: {} with pattern: {} and pageable : {}", user, pattern, pageable);

        Page<FriendData> friends = profileRepository.findFriendsByProfileIdAndPattern(user.profileId(),
                pattern.toLowerCase(Locale.ROOT), pageable);

        return mapFriendDataPageToFriendResponse(friends, user.profileId());
    }

    @Override
    public final SocialPage<FriendFeedResponse> getFriendsFeedOptions(String profileId, Pageable pageable) {
        log.info("Getting friends feed options for user : {} with pageable : {}", profileId, pageable);

        Page<Profile> friends = profileRepository.findOnlineFriendsByProfileId(profileId, pageable);

        log.info("Found online friends for feed : {}", friends.getNumberOfElements());

        return SocialPageImpl.of(friends.map(profileMapper::mapProfileToFriendFeedResponse));
    }

    @Override
    public final SocialPage<FriendSuggestionResponse> getFriendRequests(User user, Pageable pageable) {
        log.info("Getting friend requests for user : {}", user);

        Page<FriendSuggestion> friendRequests = profileRepository
                .findFriendRequestsByProfileId(user.profileId(), pageable);

        log.info("Found {} friend requests", friendRequests.getNumberOfElements());

        return SocialPageImpl.of(friendRequests.map(profileMapper::mapFriendSuggestionToFriendSuggestionResponse));
    }

    @Override
    public final SocialPage<FriendSuggestionResponse> getFriendSuggestions(User user, Pageable pageable) {
        log.info("Getting friend suggestions for user : {}", user);

        Page<FriendSuggestion> friendSuggestions = profileRepository.findProfileSuggestions(user.profileId(), pageable);

        log.info("Found {} friend suggestions", friendSuggestions.getNumberOfElements());

        return SocialPageImpl.of(friendSuggestions.map(profileMapper::mapFriendSuggestionToFriendSuggestionResponse));
    }

    @Override
    public final FriendResponse removeUserFromFriends(User user, String friendId) {
        log.info("Removing friend with id: {} from user : {}", friendId, user);

        String profileId = user.profileId();
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
    public final FriendResponse addUserToFriends(User user, String friendId) {
        log.info("Adding friend with id: {} to user : {}", friendId, user);

        String profileId = user.profileId();
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
    public final SocialPage<String> getFollowedProfileIds(User user, int pageNumber) {
        log.info("Getting friends ids for user : {}", user);

        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);

        Page<String> friendsIds = profileRepository.findFollowedIdsByProfileId(user.profileId(), pageable);

        log.info("Found {} friends ids", friendsIds.getNumberOfElements());

        return SocialPageImpl.of(friendsIds);
    }

    @Override
    public final FriendResponse addProfileToFollowed(User user, String toFollowId) {
        log.info("Adding follow status for user : {} and user with id: {}", user, toFollowId);

        String profileId = user.profileId();
        Profile profile = getProfileAndCheckFriendExistence(profileId, toFollowId);

        if (profileRepository.doesUserFollowProfile(profileId, toFollowId)) {
            throw new UserAlreadyFollows(profileId, toFollowId);
        }
        log.info("Following user with id : {} ...", toFollowId);

        profileRepository.createFollowsRelation(profileId, toFollowId);

        return profileMapper.mapProfileToFriendResponse(profile, -1, false);
    }

    @Override
    public final FriendResponse removeProfileFromFollowed(User user, String toFollowId) {
        log.info("Removing follow status for user : {} and user with id: {}", user, toFollowId);

        String profileId = user.profileId();
        Profile profile = getProfileAndCheckFriendExistence(profileId, toFollowId);

        if (!profileRepository.doesUserFollowProfile(profileId, toFollowId)) {
            throw new UserAlreadyFollows(profileId, toFollowId);
        }
        log.info("Unfollowing user with id : {} ...", toFollowId);

        profileRepository.deleteFollowsRelation(profileId, toFollowId);

        return profileMapper.mapProfileToFriendResponse(profile, -1, false);
    }

    @Override
    public final FriendResponse sendFriendRequest(User user, String friendId) {
        log.info("Sending friend request from user : {} to user with id: {}", user, friendId);

        String profileId = user.profileId();
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
    public final FriendResponse acceptFriendRequest(User user, String friendId) {
        log.info("Accepting friend request from user : {} to user with id: {}", user, friendId);

        String profileId = user.profileId();
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
    public final FriendResponse declineFriendRequest(User user, String friendId) {
        log.info("Declining friend request from user : {} to user with id: {}", user, friendId);

        String profileId = user.profileId();
        Profile profile = getProfileAndCheckFriendExistence(profileId, friendId);

        if (!profileRepository.doesUserWantToBeFriendWith(profileId, friendId)) {
            throw new UserDidNotSendFriendRequestException(profileId, friendId);
        }
        log.info("Declining friend request. from user : {} ...", profileId);

        profileRepository.deleteWantsToBeFriendWithRelation(profileId, friendId);

        return profileMapper.mapProfileToFriendResponse(profile, -1, false);
    }

    private SocialPage<FriendResponse> mapFriendDataPageToFriendResponse(Page<FriendData> friends, String profileId) {
        log.info("Found {} friends for profile with id: {}", friends.getNumberOfElements(), profileId);

        return SocialPageImpl.of(friends.map(profileMapper::mapFriendDataToFriendResponse));
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
