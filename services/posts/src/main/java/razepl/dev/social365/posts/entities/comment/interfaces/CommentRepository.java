package razepl.dev.social365.posts.entities.comment.interfaces;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.posts.entities.comment.Comment;

import java.util.UUID;

@Repository
public interface CommentRepository extends CassandraRepository<Comment, UUID>{
}
