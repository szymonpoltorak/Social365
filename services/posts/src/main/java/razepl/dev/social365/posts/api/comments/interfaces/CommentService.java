package razepl.dev.social365.posts.api.comments.interfaces;

import razepl.dev.social365.posts.api.comments.data.CommentAddRequest;
import razepl.dev.social365.posts.api.comments.data.CommentEditRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.config.auth.User;
import razepl.dev.social365.posts.entities.comment.data.CommentKeyResponse;
import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.interfaces.SocialPage;

public interface CommentService {

    SocialPage<CommentResponse> getCommentsForPost(String postId, String profileId, PageInfo pageInfo);

    CommentResponse addCommentToPost(User user, CommentAddRequest commentRequest);

    CommentResponse editComment(User user, CommentEditRequest commentEditRequest);

    CommentResponse deleteComment(User user, CommentKeyResponse commentKey);

    CommentResponse updateLikeCommentCount(User user, CommentKeyResponse commentKey);

}
