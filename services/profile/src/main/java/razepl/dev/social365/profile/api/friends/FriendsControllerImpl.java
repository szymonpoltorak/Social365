package razepl.dev.social365.profile.api.friends;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.profile.api.friends.constants.FriendMappings;
import razepl.dev.social365.profile.api.friends.data.FriendFeedResponse;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;
import razepl.dev.social365.profile.api.friends.interfaces.FriendsController;
import razepl.dev.social365.profile.api.friends.interfaces.FriendsService;
import razepl.dev.social365.profile.api.profile.constants.Params;
import razepl.dev.social365.profile.config.AuthUser;
import razepl.dev.social365.profile.config.User;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = FriendMappings.FRIENDS_MAPPING)
public class FriendsControllerImpl implements FriendsController {

    private final FriendsService friendsService;

    @Override
    @GetMapping(value = FriendMappings.GET_FRIENDS_ON_PAGE)
    public final Page<FriendResponse> getFriends(@AuthUser User user,
                                                 @RequestParam(Params.PAGE_NUMBER) int pageNumber,
                                                 @RequestParam(Params.PAGE_SIZE) int pageSize) {
        return friendsService.getFriends(user, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    @GetMapping(value = FriendMappings.GET_FRIENDS_BY_PATTERN)
    public final Page<FriendResponse> getFriendsByPattern(@AuthUser User user,
                                                          @RequestParam(Params.PATTERN)String pattern,
                                                          @RequestParam(Params.PAGE_NUMBER) int pageNumber,
                                                          @RequestParam(Params.PAGE_SIZE) int pageSize) {
        return friendsService.getFriendsByPattern(user, pattern, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    @GetMapping(value = FriendMappings.GET_FRIENDS_FEED_OPTIONS)
    public final Page<FriendFeedResponse> getFriendsFeedOptions(@RequestParam(Params.PROFILE_ID) String profileId,
                                                                @RequestParam(Params.PAGE_NUMBER) int pageNumber,
                                                                @RequestParam(Params.PAGE_SIZE) int pageSize) {
        return friendsService.getFriendsFeedOptions(profileId, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    @GetMapping(value = FriendMappings.GET_FOLLOWED_IDS)
    public final Page<String> getFollowedProfileIds(@AuthUser User user,
                                                    @RequestParam(Params.PAGE_NUMBER) int pageNumber) {
        return friendsService.getFollowedProfileIds(user, pageNumber);
    }

    @Override
    @GetMapping(value = FriendMappings.FRIEND_REQUESTS)
    public final Page<FriendSuggestionResponse> getFriendRequests(@AuthUser User user,
                                                                  @RequestParam(Params.PAGE_NUMBER) int pageNumber,
                                                                  @RequestParam(Params.PAGE_SIZE) int pageSize) {
        return friendsService.getFriendRequests(user, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    @GetMapping(value = FriendMappings.FRIEND_SUGGESTIONS)
    public final Page<FriendSuggestionResponse> getFriendSuggestions(@AuthUser User user,
                                                                     @RequestParam(Params.PAGE_NUMBER) int pageNumber,
                                                                     @RequestParam(Params.PAGE_SIZE) int pageSize) {
        return friendsService.getFriendSuggestions(user, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    @DeleteMapping(value = FriendMappings.REMOVE_FRIEND)
    public final FriendResponse removeUserFromFriends(@AuthUser User user,
                                                      @RequestParam(Params.FRIEND_ID) String friendId) {
        return friendsService.removeUserFromFriends(user, friendId);
    }

    @Override
    @PostMapping(value = FriendMappings.ADD_FRIEND)
    public final FriendResponse addUserToFriends(@AuthUser User user,
                                                 @RequestParam(Params.FRIEND_ID) String friendId) {
        return friendsService.addUserToFriends(user, friendId);
    }

    @Override
    @PutMapping(value = FriendMappings.ADD_PROFILE_TO_FOLLOWERS)
    public final FriendResponse addProfileToFollowed(@AuthUser User user,
                                                     @RequestParam(Params.TO_FOLLOW_ID) String toFollowId) {
        return friendsService.addProfileToFollowed(user, toFollowId);
    }

    @Override
    @DeleteMapping(value = FriendMappings.REMOVE_PROFILE_FROM_FOLLOWERS)
    public final FriendResponse removeProfileFromFollowed(@AuthUser User user,
                                                          @RequestParam(Params.TO_FOLLOW_ID) String toFollowId) {
        return friendsService.removeProfileFromFollowed(user, toFollowId);
    }

    @Override
    @PostMapping(value = FriendMappings.SEND_FRIEND_REQUEST)
    public final FriendResponse sendFriendRequest(@AuthUser User user,
                                                  @RequestParam(Params.FRIEND_ID) String friendId) {
        return friendsService.sendFriendRequest(user, friendId);
    }

    @Override
    @PutMapping(value = FriendMappings.ACCEPT_FRIEND_REQUEST)
    public final FriendResponse acceptFriendRequest(@AuthUser User user,
                                                    @RequestParam(Params.FRIEND_ID) String friendId) {
        return friendsService.acceptFriendRequest(user, friendId);
    }

    @Override
    @DeleteMapping(value = FriendMappings.DECLINE_FRIEND_REQUEST)
    public final FriendResponse declineFriendRequest(@AuthUser User user,
                                                     @RequestParam(Params.FRIEND_ID) String friendId) {
        return friendsService.declineFriendRequest(user, friendId);
    }
}
