package razepl.dev.social365.posts.entities.comment.interfaces;

import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.entities.comment.Comment;
import razepl.dev.social365.posts.entities.comment.CommentKey;
import razepl.dev.social365.posts.entities.post.data.CommentKeyResponse;

public interface CommentMapper {

    CommentResponse toCommentResponse(Comment comment, String profileId);

    CommentKey toCommentKey(CommentKeyResponse commentKeyResponse);

}
