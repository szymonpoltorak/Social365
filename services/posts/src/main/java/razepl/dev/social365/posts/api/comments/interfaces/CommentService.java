package razepl.dev.social365.posts.api.comments.interfaces;

import razepl.dev.social365.posts.api.comments.data.CommentRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;

public interface CommentService {

    CassandraPage<CommentResponse> getRepliesForComment(String commentId, String profileId, PageInfo pageInfo);

    CassandraPage<CommentResponse> getCommentsForPost(String postId, String profileId, PageInfo pageInfo);

    CommentResponse addCommentToPost(CommentRequest commentRequest);

    CommentResponse editComment(CommentRequest commentRequest);

    CommentResponse deleteComment(String commentId, String profileId);

}
