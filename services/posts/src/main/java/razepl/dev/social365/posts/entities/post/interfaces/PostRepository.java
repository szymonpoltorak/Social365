package razepl.dev.social365.posts.entities.post.interfaces;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.posts.entities.post.Post;

import java.util.UUID;

@Repository
public interface PostRepository extends CassandraRepository<Post, UUID>{
}
