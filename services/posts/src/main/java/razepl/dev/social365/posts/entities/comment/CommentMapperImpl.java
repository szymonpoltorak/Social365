package razepl.dev.social365.posts.entities.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.clients.images.ImageService;
import razepl.dev.social365.posts.clients.profile.ProfileService;
import razepl.dev.social365.posts.clients.profile.data.Profile;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentMapper;
import razepl.dev.social365.posts.entities.post.data.CommentKeyResponse;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentMapperImpl implements CommentMapper {

    private final ProfileService profileService;
    private final ImageService imageService;

    @Override
    public final CommentResponse toCommentResponse(Comment comment, String profileId) {
        if (comment == null) {
            log.warn("Comment is null");

            return null;
        }
        if (profileId == null) {
            log.warn("ProfileId is null");

            return null;
        }
        Profile author = profileService.getProfileDetails(comment.getAuthorId());

        String imageUrl = comment.isHasAttachments() ? imageService
                .getCommentImage(comment.getCommentId().toString()).imagePath() : "";

        return CommentResponse
                .builder()
                .isLiked(comment.isLikedBy(profileId))
                .commentKey(toCommentKeyResponse(comment.getKey()))
                .commentLikesCount(comment.getLikesCount())
                .content(comment.getContent())
                .imageUrl(imageUrl)
                .author(author)
                .build();
    }

    @Override
    public final CommentKey toCommentKey(CommentKeyResponse commentKeyResponse) {
        return CommentKey
                .builder()
                .commentId(UUID.fromString(commentKeyResponse.commentId()))
                .postId(UUID.fromString(commentKeyResponse.postId()))
                .creationDateTime(commentKeyResponse.creationDateTime())
                .build();
    }

    private CommentKeyResponse toCommentKeyResponse(CommentKey commentKey) {
        return CommentKeyResponse
                .builder()
                .commentId(commentKey.getCommentId().toString())
                .postId(commentKey.getPostId().toString())
                .creationDateTime(commentKey.getCreationDateTime())
                .build();
    }

}
