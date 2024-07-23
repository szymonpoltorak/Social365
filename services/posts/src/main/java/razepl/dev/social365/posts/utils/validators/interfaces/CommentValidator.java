package razepl.dev.social365.posts.utils.validators.interfaces;

import razepl.dev.social365.posts.api.comments.data.CommentEditRequest;

@FunctionalInterface
public interface CommentValidator {

    void validateCommentRequest(CommentEditRequest commentEditRequest);

}
