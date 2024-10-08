package razepl.dev.social365.profile.clients.posts.comments;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import razepl.dev.social365.profile.api.profile.constants.Params;
import razepl.dev.social365.profile.clients.posts.comments.constants.CommentRequest;
import razepl.dev.social365.profile.clients.posts.comments.constants.CommentResponse;
import razepl.dev.social365.profile.clients.posts.comments.constants.PostCommentsMappings;
import razepl.dev.social365.profile.clients.posts.comments.constants.PostParams;
import razepl.dev.social365.profile.clients.posts.comments.data.PostResponse;
import razepl.dev.social365.profile.utils.handler.MicroserviceErrorDecoder;

@FeignClient(name = "POSTS-COMMENTS", url = "http://posts-comments:8082", configuration = {MicroserviceErrorDecoder.class})
public interface PostCommentsService {

    @GetMapping(value = PostCommentsMappings.GET_USERS_POST_COUNT)
    int getUsersPostCount(@RequestParam(Params.PROFILE_ID) String profileId);

    @PostMapping(value = PostCommentsMappings.CREATE_POST)
    PostResponse createPost(@RequestParam(Params.PROFILE_ID) String profileId,
                            @RequestParam(PostParams.CONTENT) String content,
                            @RequestParam(PostParams.HAS_ATTACHMENTS) boolean hasAttachments);

    @PostMapping(value = PostCommentsMappings.ADD_COMMENT_TO_POST)
    CommentResponse addCommentToPost(@RequestBody CommentRequest commentRequest);

}
