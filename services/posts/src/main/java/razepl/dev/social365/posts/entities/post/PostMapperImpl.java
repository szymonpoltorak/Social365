package razepl.dev.social365.posts.entities.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import razepl.dev.social365.posts.api.posts.data.PostKeyResponse;
import razepl.dev.social365.posts.api.posts.data.PostResponse;
import razepl.dev.social365.posts.api.posts.data.PostStatistics;
import razepl.dev.social365.posts.api.posts.data.SharedPostResponse;
import razepl.dev.social365.posts.api.posts.interfaces.PostData;
import razepl.dev.social365.posts.clients.images.ImageService;
import razepl.dev.social365.posts.clients.images.data.PostImage;
import razepl.dev.social365.posts.clients.profile.ProfileService;
import razepl.dev.social365.posts.clients.profile.data.Profile;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentRepository;
import razepl.dev.social365.posts.entities.post.data.SharingPostKey;
import razepl.dev.social365.posts.entities.post.interfaces.PostMapper;
import razepl.dev.social365.posts.entities.post.interfaces.PostRepository;
import razepl.dev.social365.posts.utils.exceptions.PostDoesNotExistException;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostMapperImpl implements PostMapper {

    private final ProfileService profileService;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ImageService imageService;

    @Override
    public final PostData toPostResponse(Post post, String profileId) {
        if (post == null) {
            log.warn("Post is null");

            return null;
        }
        if (profileId == null) {
            log.warn("ProfileId is null");

            return null;
        }
        Profile author = profileService.getProfileDetails(post.getAuthorId());
        List<String> imagePaths = getImagePaths(post);

        PostStatistics statistics = PostStatistics
                .builder()
                .likes(post.getLikesCount())
                .shares(post.getSharesCount())
                .comments(commentRepository.countAllByPostId(post.getPostId()))
                .build();

        return buildPostResponse(author, post, profileId, imagePaths, statistics);
    }

    @Override
    public final PostData toPostResponseNoImages(Post post, String profileId) {
        if (post == null) {
            log.warn("Post is null");

            return null;
        }
        if (profileId == null) {
            log.warn("ProfileId is null");

            return null;
        }
        Profile author = profileService.getProfileDetails(post.getAuthorId());

        PostStatistics statistics = PostStatistics
                .builder()
                .likes(post.getLikesCount())
                .shares(post.getSharesCount())
                .comments(commentRepository.countAllByPostId(post.getPostId()))
                .build();

        return buildPostResponse(author, post, profileId, List.of(), statistics);
    }

    @Override
    public final PostData toPostData(Post post, String profileId) {
        if (post.isSharedPost()) {
            return toSharedPostResponse(post, profileId);
        }
        return toPostResponse(post, profileId);
    }

    @Override
    public final PostData toSharedPostResponse(Post sharingPost, String profileId) {
        SharingPostKey originalPostKey = sharingPost.getSharingPostKey();
        Post sharedPost = postRepository.findByPostId(originalPostKey.authorId(), originalPostKey.postId())
                .orElseThrow(() -> new PostDoesNotExistException(sharingPost.getOriginalPostId().toString(), originalPostKey.authorId()));

        return toSharedPostResponse(sharingPost, sharedPost, profileId);
    }

    @Override
    public final PostData toSharedPostResponse(Post sharingPost, Post sharedPost, String profileId) {
        return SharedPostResponse
                .builder()
                .sharingPost(toPostResponse(sharingPost, profileId))
                .sharedPost(toPostResponse(sharedPost, profileId))
                .build();
    }

    private List<String> getImagePaths(Post post) {
        if (post.isHasAttachments()) {
            return imageService
                    .getPostImages(post.getPostId().toString())
                    .stream()
                    .map(PostImage::imagePath)
                    .toList();
        }
        return List.of();
    }

    private PostResponse buildPostResponse(Profile author, Post post, String profileId,
                                           List<String> imagePaths, PostStatistics statistics) {
        return PostResponse
                .builder()
                .postKey(new PostKeyResponse(author, post.getPostId().toString(), post.getCreationDateTime()))
                .isPostLiked(post.isLikedBy(profileId))
                .areNotificationTurnedOn(post.areNotificationsTurnedOnBy(profileId))
                .statistics(statistics)
                .isBookmarked(post.isBookmarkedBy(profileId))
                .content(post.getContent())
                .imageUrls(imagePaths)
                .build();
    }
}
