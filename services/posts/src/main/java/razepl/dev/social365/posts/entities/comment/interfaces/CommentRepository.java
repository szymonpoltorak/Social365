package razepl.dev.social365.posts.entities.comment.interfaces;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.posts.api.constants.Params;
import razepl.dev.social365.posts.entities.comment.Comment;

import java.util.UUID;

@Repository
public interface CommentRepository extends CassandraRepository<Comment, UUID> {

    @Query("select count(*) from comments where post_id = :objectId")
    int countAllByPostId(@Param(Params.POST_ID) UUID postId);

    @Query("delete from comments where post_id = :objectId")
    void deleteAllByPostId(UUID postId);

    @Query("select * from comments where post_id = :objectId")
    Page<Comment> findAllByPostId(String postId, Pageable pageable);
}
