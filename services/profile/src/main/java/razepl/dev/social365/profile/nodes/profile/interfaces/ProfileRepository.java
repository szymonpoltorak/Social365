package razepl.dev.social365.profile.nodes.profile.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
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
            MATCH (p:Profile)<-[:FRIENDS_WITH]-(f:Profile)
            WHERE p.profileId = $profileId and f.profileId = $friendId
            RETURN COUNT(f) > 0
            """)
    boolean areUsersFriends(String profileId, String friendId);

    @Query("""
            MATCH (p:Profile)-[:FOLLOWS]->(f:Profile)
            WHERE p.profileId = $profileId and f.profileId = $toFollowId
            RETURN COUNT(p) > 0
            """)
    boolean doesUserFollowProfile(String profileId, String toFollowId);

    @Query("""
            MATCH (p:Profile)-[:WANTS_TO_BE_FRIEND_WITH]->(f:Profile)
            WHERE p.profileId = $profileId and f.profileId = $friendId
            RETURN COUNT(p) > 0
            """)
    boolean doesUserWantToBeFriendWith(String profileId, String friendId);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (f:Profile {profileId: $friendId})
            CREATE (p)-[:FRIENDS_WITH]->(f)
            """)
    void createFriendsWithRelation(String profileId, String friendId);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (f:Profile {profileId: $toFollowId})
            CREATE (p)-[:FOLLOWED_BY]->(f)
            """)
    void createFollowsRelation(String profileId, String toFollowId);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (f:Profile {profileId: $friendId})
            CREATE (p)-[:WANTS_TO_BE_FRIEND_WITH]->(f)
            """)
    void createWantsToBeFriendWithRelation(String profileId, String friendId);

    @Query("""
            MATCH(p:Profile)-[fr:FRIENDS_WITH]-(f:Profile)
            WHERE p.profileId = $profileId and f.profileId = $friendId
            DELETE fr
            """)
    void deleteFriendshipRelationship(String profileId, String friendId);

    @Query("""
            MATCH(p:Profile)-[fr:WANTS_TO_BE_FRIEND_WITH]-(f:Profile)
            WHERE p.profileId = $profileId and f.profileId = $friendId
            DELETE fr
            """)
    void deleteWantsToBeFriendWithRelation(String profileId, String friendId);

    @Query("""
            MATCH(p:Profile)-[fr:FOLLOWS]-(f:Profile)
            WHERE p.profileId = $profileId and f.profileId = toFollow
            DELETE fr
            """)
    void deleteFollowsRelation(String profileId, String toFollowId);

    //TODO: Review the queries below
    @Query(
            value = """
                    MATCH (p:Profile)<-[:FRIENDS_WITH]-(f:Profile)
                    WHERE p.profileId = $profileId
                    WITH f, EXISTS((f)<-[:FOLLOWS]-(p)) as isFollowed
                    OPTIONAL MATCH (p)-[:FRIENDS_WITH]->(f1:Profile)<-[:FRIENDS_WITH]-(f)
                    RETURN f, COUNT(f1) as mutualFriendsCount, isFollowed
                    SKIP $skip LIMIT $limit
                    """,
            countQuery = """
                    MATCH (p:Profile)-[:FRIENDS_WITH]->(f:Profile)
                    WHERE p.profileId = $profileId
                    RETURN COUNT(f)
                    """
    )
    Page<FriendData> findFriendsByProfileId(@Param(ProfileParams.PROFILE_ID) String profileId,
                                            Pageable pageable);

    @Query(
            value = """
                    MATCH (f:Profile)<-[:FOLLOWS]-(p:Profile)
                    WHERE p.profileId = $profileId
                    RETURN f.profileId
                    SKIP $skip LIMIT $limit
                    """,
            countQuery = """
                    MATCH (f:Profile)<-[:FOLLOWS]-(p:Profile)
                    WHERE p.profileId = $profileId
                    RETURN COUNT(f)
                    """
    )
    Page<String> findFollowedIdsByProfileId(@Param(ProfileParams.PROFILE_ID) String profileId, Pageable pageable);

    @Query(
            value = """
                    MATCH (f:Profile)-[:WANTS_TO_BE_FRIEND_WITH]->(p:Profile)
                    WHERE p.profileId = $profileId
                    OPTIONAL MATCH (p)-[:FRIENDS_WITH]->(f1:Profile)<-[:FRIENDS_WITH]-(f)
                    RETURN f, COUNT(f1) as mutualFriendsCount
                    SKIP $skip LIMIT $limit
                    """,
            countQuery = """
                    MATCH (f:Profile)-[:WANTS_TO_BE_FRIEND_WITH]->(p:Profile)
                    WHERE p.profileId = $profileId
                    RETURN COUNT(f)
                    """
    )
    Page<FriendSuggestion> findFriendRequestsByProfileId(@Param(ProfileParams.PROFILE_ID) String profileId,
                                                         Pageable pageable);

    @Query(
            value = """
                    MATCH (p1:Profile)-[:FRIENDS_WITH]->(f:Profile)<-[:FRIENDS_WITH]-(p:Profile)
                    WHERE NOT (p)<-[:FRIENDS_WITH]-(p1:Profile) AND p.profileId = $profileId
                    RETURN p1, COUNT(f) as mutualFriendsCount
                    ORDER BY mutualFriends DESC
                    SKIP $skip LIMIT $limit
                    """,
            countQuery = """
                    MATCH (p1:Profile)-[:FRIENDS_WITH]->(f:Profile)<-[:FRIENDS_WITH]-(p:Profile)
                    WHERE NOT (p)<-[:FRIENDS_WITH]-(p1:Profile) AND p.profileId = $profileId
                    RETURN COUNT(p1)
                    """
    )
    Page<FriendSuggestion> findProfileSuggestions(@Param(ProfileParams.PROFILE_ID) String profileId,
                                                  Pageable pageable);

}
