package razepl.dev.social365.posts.entities.post.interfaces;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.posts.api.constants.Params;
import razepl.dev.social365.posts.entities.post.Post;
import razepl.dev.social365.posts.entities.post.PostKey;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends CassandraRepository<Post, PostKey> {

    @Query("""
            select * from posts
            where author_id in :followedIds
            """)
    Slice<Post> findAllByFollowedUserIdsOrProfileId(@Param(Params.FOLLOWED_IDS) List<String> followedIds, Pageable pageable);

    @Query("""
            select * from posts
            where author_id = :authorId
            """)
    Slice<Post> findAllByAuthorId(@Param(Params.AUTHOR_ID) String authorId, Pageable pageable);

    @Query("select count(*) from posts where author_id = :authorId")
    int countAllByAuthorId(@Param(Params.AUTHOR_ID) String authorId);

    @Query("select * from posts where author_id = :authorId and post_id = :postId")
    Optional<Post> findByPostId(@Param(Params.AUTHOR_ID) String authorId, @Param(Params.POST_ID) UUID postId);

    @Query("""
            delete from posts
            where author_id = :authorId and post_id = :postId and creation_date_time = :creationDateTime
            """)
    void deleteByPostId(@Param(Params.POST_ID) UUID postId,
                        @Param(Params.CREATION_DATE_TIME) String creationDateTime,
                        @Param(Params.AUTHOR_ID) String authorId);
}
