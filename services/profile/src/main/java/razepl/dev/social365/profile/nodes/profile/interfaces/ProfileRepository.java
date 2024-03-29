package razepl.dev.social365.profile.nodes.profile.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.api.profile.constants.ProfileParams;
import razepl.dev.social365.profile.nodes.profile.Profile;

import java.util.Optional;

@Repository
public interface ProfileRepository extends Neo4jRepository<Profile, String> {

    @Query("MATCH (p:Profile) WHERE p.profileId = $profileId RETURN p")
    Optional<Profile> findByProfileId(@Param(ProfileParams.PROFILE_ID) String profileId);

    @Query("MATCH (p:Profile)-[:FRIENDS_WITH]->(f:Profile) WHERE p.profileId = $profileId RETURN f")
    Page<Profile> findFriendsByProfileId(@Param(ProfileParams.PROFILE_ID) String profileId, Pageable pageable);

    @Query("MATCH (f:Profile)-[:WANTS_TO_BE_FRIEND_WITH]->(p:Profile) WHERE p.profileId = $profileId RETURN f")
    Page<Profile> findFriendRequestsByProfileId(@Param(ProfileParams.PROFILE_ID) String profileId, Pageable pageable);

    @Query("""
            MATCH (p1:Profile)-[:FRIENDS_WITH]->(f:Profile)<-[:FRIENDS_WITH]-(p2:Profile)
            WHERE p1.profileId = $profileId AND p2.profileId = $friendId
            RETURN COUNT(f)
            """)
    int countMutualFriends(String profileId, String friendId);

    @Query("""
            MATCH (p:Profile)-[:FOLLOWED_BY]->(f:Profile)
            WHERE p.profileId = $profileId AND f.profileId = $friendId RETURN COUNT(f) > 0
            """)
    boolean isProfileFollowedByFriend(String profileId, String friendId);
}
