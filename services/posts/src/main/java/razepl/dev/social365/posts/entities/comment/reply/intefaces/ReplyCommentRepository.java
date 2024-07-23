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
import razepl.dev.social365.posts.entities.comment.reply.data.ReplyKeyResponse;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReplyCommentRepository extends CassandraRepository<ReplyComment, ReplyCommentKey> {

    @Query("select * from reply_comments where reply_to_comment_id = :commentId ALLOW FILTERING")
    Slice<ReplyComment> findAllRepliesByCommentId(@Param(Params.COMMENT_ID) UUID commentId, Pageable pageable);

    @Query("select * from reply_comments where reply_to_comment_id = :commentId and reply_comment_id = :replyId and creation_date_time = :creationDateTime")
    Optional<ReplyComment> findReplyById(@Param(Params.COMMENT_ID) UUID commentId,
                                        @Param(Params.REPLY_ID) UUID replyId,
                                        @Param(Params.CREATION_DATE_TIME) String creationDateTime);

    default Optional<ReplyComment> findByKey(ReplyCommentKey key) {
        return findReplyById(key.getReplyToCommentId(), key.getReplyCommentId(), key.getCreationDateTime());
    }

    @Query("delete from reply_comments where reply_to_comment_id = :commentId")
    void deleteAllByCommentId(@Param(Params.COMMENT_ID) UUID commentId);

}
