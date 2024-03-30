package razepl.dev.social365.profile.nodes.profile.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.api.friends.constants.FriendsParams;
import razepl.dev.social365.profile.api.friends.data.FriendData;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestion;
import razepl.dev.social365.profile.api.profile.constants.ProfileParams;
import razepl.dev.social365.profile.nodes.profile.Profile;

import java.util.Optional;

@Repository
public interface ProfileRepository extends Neo4jRepository<Profile, String> {

    @Query("MATCH (p:Profile) WHERE p.profileId = $profileId RETURN p")
    Optional<Profile> findByProfileId(@Param(ProfileParams.PROFILE_ID) String profileId);

    @Query("""
            MATCH (p:Profile)-[:FRIENDS_WITH]->(f:Profile)
            WHERE p.profileId = $profileId
            WITH f, EXISTS((f)-[:FOLLOWED_BY]->(p)) as isFollowed
            OPTIONAL MATCH (p)-[:FRIENDS_WITH]->(f1:Profile)<-[:FRIENDS_WITH]-(f)
            RETURN f, COUNT(f1) as mutualFriendsCount, isFollowed
            """)
    Page<FriendData> findFriendsByProfileId(@Param(ProfileParams.PROFILE_ID) String profileId,
                                            Pageable pageable);

    @Query("""
            MATCH (f:Profile)-[:WANTS_TO_BE_FRIEND_WITH]->(p:Profile)
            WHERE p.profileId = $profileId
            RETURN f, COUNT(f) as mutualFriendsCount
            """)
    Page<FriendSuggestion> findFriendRequestsByProfileId(@Param(ProfileParams.PROFILE_ID) String profileId,
                                                         Pageable pageable);

    @Query("""
            MATCH (p1:Profile)-[:FRIENDS_WITH]->(f:Profile)<-[:FRIENDS_WITH]-(p:Profile)
            WHERE NOT (p)<-[:FRIENDS_WITH]-(p1:Profile) AND p.profileId = $profileId
            RETURN p1, COUNT(f) as mutualFriendsCount
            ORDER BY mutualFriends DESC
            """)
    Page<FriendSuggestion> findProfileSuggestions(@Param(ProfileParams.PROFILE_ID) String profileId,
                                                  Pageable pageable);
}
