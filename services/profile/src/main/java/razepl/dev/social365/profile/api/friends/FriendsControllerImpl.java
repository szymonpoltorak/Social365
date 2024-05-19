package razepl.dev.social365.profile.api.friends;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.profile.api.friends.constants.FriendMappings;
import razepl.dev.social365.profile.api.friends.constants.FriendsParams;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;
import razepl.dev.social365.profile.api.friends.interfaces.FriendsController;
import razepl.dev.social365.profile.api.friends.interfaces.FriendsService;
import razepl.dev.social365.profile.api.profile.constants.ProfileMappings;
import razepl.dev.social365.profile.api.profile.constants.ProfileParams;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = FriendMappings.FRIENDS_MAPPING)
public class FriendsControllerImpl implements FriendsController {

    private final FriendsService friendsService;

    @Override
    @GetMapping(value = FriendMappings.GET_FRIENDS_ON_PAGE)
    public final Page<FriendResponse> getFriends(@RequestParam(ProfileParams.PROFILE_ID) String profileId,
                                                 Pageable pageable) {
        return friendsService.getFriends(profileId, pageable);
    }

    @Override
    @GetMapping(value = ProfileMappings.GET_FOLLOWED_IDS)
    public final Page<String> getFollowedProfileIds(@RequestParam(ProfileParams.PROFILE_ID) String profileId,
                                                    @RequestParam(ProfileParams.PAGE_NUMBER) int pageNumber) {
        return friendsService.getFollowedProfileIds(profileId, pageNumber);
    }

    @Override
    @GetMapping(value = FriendMappings.FRIEND_REQUESTS)
    public final Page<FriendSuggestionResponse> getFriendRequests(@RequestParam(ProfileParams.PROFILE_ID) String profileId,
                                                                 Pageable pageable) {
        return friendsService.getFriendRequests(profileId, pageable);
    }

    @Override
    @GetMapping(value = FriendMappings.FRIEND_SUGGESTIONS)
    public final Page<FriendSuggestionResponse> getFriendSuggestions(@RequestParam(ProfileParams.PROFILE_ID) String profileId,
                                                                    Pageable pageable) {
        return friendsService.getFriendSuggestions(profileId, pageable);
    }

    @Override
    @DeleteMapping(value = FriendMappings.REMOVE_FRIEND)
    public final FriendResponse removeUserFromFriends(@RequestParam(ProfileParams.PROFILE_ID) String profileId,
                                                      @RequestParam(FriendsParams.FRIEND_ID) String friendId) {
        return friendsService.removeUserFromFriends(profileId, friendId);
    }

    @Override
    @PostMapping(value = FriendMappings.ADD_FRIEND)
    public final FriendResponse addUserToFriends(@RequestParam(ProfileParams.PROFILE_ID) String profileId,
                                                 @RequestParam(FriendsParams.FRIEND_ID) String friendId) {
        return friendsService.addUserToFriends(profileId, friendId);
    }

    @Override
    @PutMapping(value = FriendMappings.ADD_PROFILE_TO_FOLLOWERS)
    public final FriendResponse addProfileToFollowed(@RequestParam(ProfileParams.PROFILE_ID) String profileId,
                                                     @RequestParam(FriendsParams.TO_FOLLOW) String toFollow) {
        return friendsService.addProfileToFollowed(profileId, toFollow);
    }

    @Override
    @DeleteMapping(value = FriendMappings.REMOVE_PROFILE_FROM_FOLLOWERS)
    public final FriendResponse removeProfileFromFollowed(String profileId, String toFollowId) {
        return friendsService.removeProfileFromFollowed(profileId, toFollowId);
    }
}
