package razepl.dev.social365.posts.api.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.posts.api.constants.Params;
import razepl.dev.social365.posts.api.posts.constants.PostMappings;
import razepl.dev.social365.posts.api.posts.interfaces.PostController;
import razepl.dev.social365.posts.api.posts.interfaces.PostData;
import razepl.dev.social365.posts.api.posts.interfaces.PostService;
import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = PostMappings.POST_MAPPING)
public class PostControllerImpl implements PostController {

    private final PostService postService;

    @Override
    @GetMapping(value = PostMappings.GET_USERS_POST_COUNT)
    public final int getUsersPostCount(@RequestParam(Params.PROFILE_ID) String profileId) {
        return postService.getUsersPostCount(profileId);
    }

    @Override
    @GetMapping(value = PostMappings.GET_POSTS_ON_PAGE)
    public final CassandraPage<PostData> getPostsOnPage(@RequestParam(Params.PROFILE_ID) String profileId,
                                                        @RequestParam(Params.FRIENDS_PAGE_NUMBER) int friendsPageNumber,
                                                        @RequestParam(Params.PAGE_SIZE) int pageSize,
                                                        @RequestParam(value = Params.PAGING_STATE, required = false) String pagingState) {
        return postService.getPostsOnPage(profileId, PageInfo.of(friendsPageNumber, pageSize, pagingState));
    }

    @Override
    @GetMapping(value = PostMappings.GET_USERS_POSTS)
    public final CassandraPage<PostData> getUsersPosts(@RequestParam(Params.PROFILE_ID) String profileId,
                                                       @RequestParam(Params.FRIENDS_PAGE_NUMBER) int friendsPageNumber,
                                                       @RequestParam(Params.PAGE_SIZE) int pageSize,
                                                       @RequestParam(value = Params.PAGING_STATE, required = false) String pagingState) {
        return postService.getUsersPosts(profileId, PageInfo.of(friendsPageNumber, pageSize, pagingState));
    }

    @Override
    @PutMapping(value = PostMappings.UPDATE_LIKE_POST_COUNT)
    public final PostData updateLikePostCount(@RequestParam(Params.PROFILE_ID) String profileId,
                                              @RequestParam(Params.POST_ID) String postId) {
        return postService.updateLikePostCount(profileId, postId);
    }

    @Override
    @PutMapping(value = PostMappings.UPDATE_NOTIFICATION_STATUS)
    public final PostData updateNotificationStatus(@RequestParam(Params.PROFILE_ID) String profileId,
                                                   @RequestParam(Params.POST_ID) String postId) {
        return postService.updateNotificationStatus(profileId, postId);
    }

    @Override
    @PutMapping(value = PostMappings.UPDATE_BOOKMARK_STATUS)
    public final PostData updateBookmarkStatus(@RequestParam(Params.PROFILE_ID) String profileId,
                                               @RequestParam(Params.POST_ID) String postId) {
        return postService.updateBookmarkStatus(profileId, postId);
    }

    @Override
    @PutMapping(value = PostMappings.SHARE_POST)
    public final PostData sharePost(@RequestParam(Params.PROFILE_ID) String profileId,
                                    @RequestParam(Params.POST_ID) String postId,
                                    @RequestParam(Params.CONTENT) String content) {
        return postService.sharePost(profileId, postId, content);
    }

    @Override
    @PostMapping(value = PostMappings.CREATE_POST)
    public final PostData createPost(@RequestParam(Params.PROFILE_ID) String profileId,
                                     @RequestParam(Params.CONTENT) String content,
                                     @RequestParam(Params.HAS_ATTACHMENTS) boolean hasAttachments) {
        return postService.createPost(profileId, content, hasAttachments);
    }

    @Override
    @PutMapping(value = PostMappings.EDIT_POST)
    public final PostData editPost(@RequestParam(Params.PROFILE_ID) String profileId,
                                   @RequestParam(Params.POST_ID) String postId,
                                   @RequestParam(Params.CONTENT) String content) {
        return postService.editPost(profileId, postId, content);
    }

    @Override
    @DeleteMapping(value = PostMappings.DELETE_POST)
    public final PostData deletePost(@RequestParam(Params.PROFILE_ID) String profileId,
                                         @RequestParam(Params.POST_ID) String postId) {
        return postService.deletePost(profileId, postId);
    }
}
