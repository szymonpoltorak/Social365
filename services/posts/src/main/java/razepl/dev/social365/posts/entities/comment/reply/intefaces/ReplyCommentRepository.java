package razepl.dev.social365.posts.entities.comment.reply.intefaces;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.posts.api.constants.Params;
import razepl.dev.social365.posts.entities.comment.reply.ReplyComment;
import razepl.dev.social365.posts.entities.comment.reply.ReplyCommentKey;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReplyCommentRepository extends CassandraRepository<ReplyComment, ReplyCommentKey> {

    @Query("select * from reply_comments where reply_to_comment_id = :commentId and post_id = :postId")
    Slice<ReplyComment> findAllRepliesByCommentId(@Param(Params.COMMENT_ID) UUID commentId,
                                                  @Param(Params.POST_ID) UUID postId,
                                                  Pageable pageable);

    @Query("""
            select * from reply_comments
            where reply_to_comment_id = :commentId
             and reply_comment_id = :replyId and creation_date_time = :authorId and post_id = :postId
            """)
    Optional<ReplyComment> findReplyById(@Param(Params.COMMENT_ID) UUID commentId,
                                         @Param(Params.REPLY_ID) UUID replyId,
                                         @Param(Params.CREATION_DATE_TIME) String creationDateTime,
                                         @Param(Params.POST_ID) UUID postId);

    default Optional<ReplyComment> findByKey(ReplyCommentKey key) {
        return findReplyById(key.getReplyToCommentId(), key.getReplyCommentId(), key.getCreationDateTime(), key.getPostId());
    }

    @Query("delete from reply_comments where reply_to_comment_id = :commentId and post_id = :postId")
    void deleteAllByCommentId(@Param(Params.COMMENT_ID) UUID commentId, @Param(Params.POST_ID) UUID postId);

    @Query("delete from reply_comments where post_id = :postId")
    void deleteAllByPostId(@Param(Params.POST_ID) UUID postId);

}
