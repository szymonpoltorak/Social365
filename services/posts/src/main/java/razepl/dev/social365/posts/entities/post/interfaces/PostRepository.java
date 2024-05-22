package razepl.dev.social365.posts.entities.post.interfaces;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.posts.api.constants.Params;
import razepl.dev.social365.posts.entities.post.Post;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PostRepository extends CassandraRepository<Post, UUID> {

    @Query("select * from posts where author_id in :followedIds order by creation_date_time desc")
    Slice<Post> findAllByFollowedUserIds(@Param(Params.FOLLOWED_IDS) List<String> followedIds, Pageable pageable);

    @Query("select count(*) from posts where author_id = :profileId")
    int countAllByAuthorId(@Param(Params.PROFILE_ID) String profileId);

    @Query("select user_liked_ids from posts where post_id = :postId")
    Set<String> findUserLikedIdsByPostId(UUID postId);

    default int countLikesByPostId(UUID postId) {
        return findUserLikedIdsByPostId(postId).size();
    }

    @Query("select user_shared_ids from posts where post_id = :postId")
    Set<String> findUserSharesByPostId(UUID postId);

    default int countSharesByPostId(UUID postId) {
        return findUserSharesByPostId(postId).size();
    }
}
