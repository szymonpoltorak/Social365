package razepl.dev.social365.profile.api.friends;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.profile.api.friends.constants.FriendMappings;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;
import razepl.dev.social365.profile.api.friends.constants.FriendsParams;
import razepl.dev.social365.profile.api.friends.interfaces.FriendsController;
import razepl.dev.social365.profile.api.friends.interfaces.FriendsService;
import razepl.dev.social365.profile.api.profile.constants.ProfileParams;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = FriendMappings.FRIENDS_MAPPING)
public class FriendsControllerImpl implements FriendsController {

    private final FriendsService friendsService;

    @Override
    @GetMapping(value = FriendMappings.GET_FRIENDS_ON_PAGE)
    public final Set<FriendResponse> getFriends(@RequestParam(ProfileParams.PROFILE_ID) String profileId,
                                                 @RequestParam(FriendsParams.PAGE_NUMBER) int page,
                                                 @RequestParam(FriendsParams.PAGE_SIZE) int size) {
        return friendsService.getFriends(profileId, page, size);
    }

    @Override
    @GetMapping(value = FriendMappings.FRIEND_REQUESTS)
    public final Set<FriendSuggestionResponse> getFriendRequests(@RequestParam(ProfileParams.PROFILE_ID) String profileId,
                                                                 @RequestParam(FriendsParams.PAGE_NUMBER) int page,
                                                                 @RequestParam(FriendsParams.PAGE_SIZE) int size) {
        return friendsService.getFriendRequests(profileId, page, size);
    }

    @Override
    @GetMapping(value = FriendMappings.FRIEND_SUGGESTIONS)
    public final Set<FriendSuggestionResponse> getFriendSuggestions(@RequestParam(ProfileParams.PROFILE_ID) String profileId,
                                                           @RequestParam(FriendsParams.PAGE_NUMBER) int page,
                                                           @RequestParam(FriendsParams.PAGE_SIZE) int size) {
        return friendsService.getFriendSuggestions(profileId, page, size);
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
}
