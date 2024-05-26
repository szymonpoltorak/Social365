package razepl.dev.social365.posts.entities.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.clients.profile.ProfileService;
import razepl.dev.social365.posts.clients.profile.data.Profile;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentMapperImpl implements CommentMapper {

    private final ProfileService profileService;

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

        //TODO: getting attachments for comments

        return CommentResponse
                .builder()
                .isLiked(comment.isLikedBy(profileId))
                .commentId(comment.getCommentId().toString())
                .commentLikesCount(comment.getLikesCount())
                .content(comment.getContent())
                .creationDateTime(comment.getCreationDateTime())
                .author(author)
                .build();
    }
}
