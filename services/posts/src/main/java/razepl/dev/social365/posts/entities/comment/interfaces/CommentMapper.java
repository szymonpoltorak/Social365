package razepl.dev.social365.posts.entities.comment.interfaces;

import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.entities.comment.Comment;

@FunctionalInterface
public interface CommentMapper {

    CommentResponse toCommentResponse(Comment comment, String profileId);

}
