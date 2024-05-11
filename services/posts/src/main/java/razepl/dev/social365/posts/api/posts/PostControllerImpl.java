package razepl.dev.social365.posts.api.posts;

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
import razepl.dev.social365.posts.api.constants.Params;
import razepl.dev.social365.posts.api.posts.constants.PostMappings;
import razepl.dev.social365.posts.api.posts.data.PostResponse;
import razepl.dev.social365.posts.api.posts.interfaces.PostController;
import razepl.dev.social365.posts.api.posts.interfaces.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = PostMappings.POST_MAPPING)
public class PostControllerImpl implements PostController {

    private final PostService postService;

    @Override
    @GetMapping(value = PostMappings.GET_POSTS_ON_PAGE)
    public final Page<PostResponse> getPostsOnPage(@RequestParam(Params.PROFILE_ID) String profileId,
                                                   Pageable pageable) {
        return postService.getPostsOnPage(profileId, pageable);
    }

    @Override
    @PutMapping(value = PostMappings.UPDATE_LIKE_POST_COUNT)
    public final PostResponse updateLikePostCount(@RequestParam(Params.PROFILE_ID) String profileId,
                                                  @RequestParam(Params.POST_ID) String postId) {
        return postService.updateLikePostCount(profileId, postId);
    }

    @Override
    @PutMapping(value = PostMappings.UPDATE_NOTIFICATION_STATUS)
    public final PostResponse updateNotificationStatus(@RequestParam(Params.PROFILE_ID) String profileId,
                                                       @RequestParam(Params.POST_ID) String postId) {
        return postService.updateNotificationStatus(profileId, postId);
    }

    @Override
    @PutMapping(value = PostMappings.UPDATE_BOOKMARK_STATUS)
    public final PostResponse updateBookmarkStatus(@RequestParam(Params.PROFILE_ID) String profileId,
                                                   @RequestParam(Params.POST_ID) String postId) {
        return postService.updateBookmarkStatus(profileId, postId);
    }

    @Override
    @PutMapping(value = PostMappings.UPDATE_SHARES_COUNT)
    public final PostResponse updateSharesCount(@RequestParam(Params.PROFILE_ID) String profileId,
                                                @RequestParam(Params.POST_ID) String postId) {
        return postService.updateSharesCount(profileId, postId);
    }

    @Override
    @PostMapping(value = PostMappings.CREATE_POST)
    public final PostResponse createPost(@RequestParam(Params.PROFILE_ID) String profileId,
                                         @RequestParam(Params.CONTENT) String content) {
        return postService.createPost(profileId, content);
    }

    @Override
    @PutMapping(value = PostMappings.EDIT_POST)
    public final PostResponse editPost(@RequestParam(Params.PROFILE_ID) String profileId,
                                       @RequestParam(Params.POST_ID) String postId,
                                       @RequestParam(Params.CONTENT) String content) {
        return postService.editPost(profileId, postId, content);
    }

    @Override
    @DeleteMapping(value = PostMappings.DELETE_POST)
    public final PostResponse deletePost(@RequestParam(Params.PROFILE_ID) String profileId,
                                         @RequestParam(Params.POST_ID) String postId) {
        return postService.deletePost(profileId, postId);
    }
}
