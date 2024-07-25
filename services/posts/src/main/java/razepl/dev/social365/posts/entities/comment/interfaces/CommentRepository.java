package razepl.dev.social365.posts.entities.comment.interfaces;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.posts.api.constants.Params;
import razepl.dev.social365.posts.entities.comment.Comment;
import razepl.dev.social365.posts.entities.comment.CommentKey;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends CassandraRepository<Comment, CommentKey> {

    @Query("select count(*) from comments where post_id = :postId ALLOW FILTERING")
    int countAllByPostId(@Param(Params.POST_ID) UUID postId);

    @Query("delete from comments where post_id = :postId ALLOW FILTERING")
    void deleteAllByPostId(@Param(Params.POST_ID) UUID postId);

    @Query("select * from comments where post_id = :postId ALLOW FILTERING")
    Slice<Comment> findAllByPostId(@Param(Params.POST_ID) UUID postId, Pageable pageable);

    @Query("select * from comments where post_id = :postId ALLOW FILTERING")
    List<Comment> findAllByPostId(@Param(Params.POST_ID) UUID postId);

    @Query("select * from comments where post_id = :postId and comment_id = :commentId and creation_date_time = :authorId")
    Optional<Comment> findCommentById(@Param(Params.POST_ID) UUID postId,
                                      @Param(Params.COMMENT_ID) UUID commentId,
                                      @Param(Params.CREATION_DATE_TIME) String creationDateTime);

    @Query("delete from comments where post_id = :postId and comment_id = :commentId and creation_date_time = :authorId")
    void deleteCommentById(@Param(Params.POST_ID) UUID postId,
                           @Param(Params.COMMENT_ID) UUID commentId,
                           @Param(Params.CREATION_DATE_TIME) String creationDateTime);

    default Optional<Comment> findCommentByKey(CommentKey key) {
        return findCommentById(key.getPostId(), key.getCommentId(), key.getCreationDateTime());
    }

    default void deleteCommentByKey(CommentKey key) {
        deleteCommentById(key.getPostId(), key.getCommentId(), key.getCreationDateTime());
    }

}
