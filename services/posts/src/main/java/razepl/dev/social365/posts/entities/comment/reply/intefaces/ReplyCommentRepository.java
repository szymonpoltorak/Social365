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

import java.util.UUID;

@Repository
public interface ReplyCommentRepository extends CassandraRepository<ReplyComment, ReplyCommentKey> {

    @Query("select * from reply_comments where reply_to_comment_id = :commentId ALLOW FILTERING")
    Slice<ReplyComment> findAllRepliesByCommentId(@Param(Params.COMMENT_ID) UUID commentId, Pageable pageable);

}
