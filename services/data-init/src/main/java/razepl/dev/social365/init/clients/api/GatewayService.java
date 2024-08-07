package razepl.dev.social365.init.clients.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import razepl.dev.social365.init.clients.api.constants.AuthMappings;
import razepl.dev.social365.init.clients.api.data.AuthResponse;
import razepl.dev.social365.init.clients.api.data.RegisterRequest;
import razepl.dev.social365.init.clients.api.constants.CommentAddRequest;
import razepl.dev.social365.init.clients.api.constants.CommentResponse;
import razepl.dev.social365.init.clients.api.constants.Params;
import razepl.dev.social365.init.clients.api.constants.PostCommentsMappings;
import razepl.dev.social365.init.clients.api.data.PostResponse;
import razepl.dev.social365.init.clients.api.constants.ProfileMappings;
import razepl.dev.social365.init.clients.api.data.FriendResponse;

@FeignClient(name = "GATEWAY-SERVICE", url = "http://api-gateway:8222")
public interface GatewayService {

    @PostMapping(value = AuthMappings.REGISTER_MAPPING)
    AuthResponse registerUser(@RequestBody RegisterRequest registerRequest);

    @GetMapping(value = PostCommentsMappings.GET_USERS_POST_COUNT)
    int getUsersPostCount(@RequestParam(Params.PROFILE_ID) String profileId);

    @PostMapping(value = PostCommentsMappings.CREATE_POST)
    PostResponse createPost(@RequestParam(Params.PROFILE_ID) String profileId,
                            @RequestParam(Params.CONTENT) String content,
                            @RequestParam(Params.HAS_ATTACHMENTS) boolean hasAttachments);

    @PostMapping(value = PostCommentsMappings.ADD_COMMENT_TO_POST)
    CommentResponse addCommentToPost(@RequestBody CommentAddRequest commentAddRequest);

    @PostMapping(value = ProfileMappings.ADD_FRIEND)
    FriendResponse addUserToFriends(@RequestParam(Params.PROFILE_ID) String profileId,
                                    @RequestParam(Params.FRIEND_ID) String friendId);

}
