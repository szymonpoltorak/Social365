package razepl.dev.social365.profile.clients.posts.comments;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import razepl.dev.social365.profile.clients.posts.comments.constants.PostCommentsMappings;

@FunctionalInterface
@FeignClient(name = "POST-COMMENTS-SERVICE")
public interface PostCommentsService {

    @GetMapping(value = PostCommentsMappings.GET_USERS_POST_COUNT)
    int getUsersPostCount(String profileId);

}
