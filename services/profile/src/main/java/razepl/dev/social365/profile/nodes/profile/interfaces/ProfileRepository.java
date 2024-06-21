package razepl.dev.social365.profile.nodes.profile.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.api.friends.data.FriendData;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestion;
import razepl.dev.social365.profile.api.profile.constants.Params;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;
import razepl.dev.social365.profile.nodes.about.workplace.Workplace;
import razepl.dev.social365.profile.nodes.profile.Profile;

import java.util.Optional;

@Repository
public interface ProfileRepository extends Neo4jRepository<Profile, String> {

    @Query("""
            MATCH (p:Profile)
            WHERE p.profileId = $profileId
            MATCH(p)-[r:HAS|BORN_ON|WORKS_AS|IS|STUDIED_AT|WENT_TO|LIVES_IN|FROM]->(e)
            RETURN p, collect(r) as rel, collect(e) as nodes
            """)
    Optional<Profile> findByProfileId(@Param(Params.PROFILE_ID) String profileId);

    @Query("""
            MATCH (e:Email)
            WHERE e.emailValue = $username
            RETURN COUNT(e) > 0
            """)
    boolean existsByUsername(@Param(Params.USERNAME) String username);

    @Query("""
            MATCH (p:Profile)-[r:HAS]->(e:Email)
            WHERE e.emailValue = $username
            MATCH(p)-[r:HAS|BORN_ON|WORKS_AS|IS|STUDIED_AT|WENT_TO|LIVES_IN|FROM]->(t)
            RETURN p, collect(r) as rel, collect(t) as nodes
            """)
    Optional<Profile> findByUsername(@Param(Params.USERNAME) String username);

    @Query("""
            MATCH (p:Profile)-[:FRIENDS_WITH]-(f:Profile)
            WHERE p.profileId = $profileId and f.profileId = $friendId
            RETURN COUNT(f) > 0
            """)
    boolean areUsersFriends(@Param(Params.PROFILE_ID) String profileId,
                            @Param(Params.FRIEND_ID) String friendId);

    @Query("""
            MATCH (p:Profile)-[:FOLLOWS]->(f:Profile)
            WHERE p.profileId = $profileId and f.profileId = $toFollowId
            RETURN COUNT(p) > 0
            """)
    boolean doesUserFollowProfile(@Param(Params.PROFILE_ID) String profileId,
                                  @Param(Params.TO_FOLLOW_ID) String toFollowId);

    @Query("""
            MATCH (p:Profile)-[:WANTS_TO_BE_FRIEND_WITH]->(f:Profile)
            WHERE p.profileId = $profileId and f.profileId = $friendId
            RETURN COUNT(p) > 0
            """)
    boolean doesUserWantToBeFriendWith(@Param(Params.PROFILE_ID) String profileId,
                                       @Param(Params.FRIEND_ID) String friendId);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (f:Profile {profileId: $friendId})
            CREATE (p)-[:FRIENDS_WITH]->(f)
            """)
    void createFriendsWithRelation(@Param(Params.PROFILE_ID) String profileId,
                                   @Param(Params.FRIEND_ID) String friendId);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (f:Profile {profileId: $toFollowId})
            CREATE (p)-[:FOLLOWS]->(f)
            """)
    void createFollowsRelation(@Param(Params.PROFILE_ID) String profileId,
                               @Param(Params.TO_FOLLOW_ID) String toFollowId);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (f:Profile {profileId: $friendId})
            CREATE (p)-[:WANTS_TO_BE_FRIEND_WITH]->(f)
            """)
    void createWantsToBeFriendWithRelation(@Param(Params.PROFILE_ID) String profileId,
                                           @Param(Params.FRIEND_ID) String friendId);

    @Query("""
            MATCH(p:Profile)-[fr:FRIENDS_WITH]-(f:Profile)
            WHERE p.profileId = $profileId and f.profileId = $friendId
            DELETE fr
            """)
    void deleteFriendshipRelationship(@Param(Params.PROFILE_ID) String profileId,
                                      @Param(Params.FRIEND_ID) String friendId);

    @Query("""
            MATCH(p:Profile)-[fr:WANTS_TO_BE_FRIEND_WITH]-(f:Profile)
            WHERE p.profileId = $profileId and f.profileId = $friendId
            DELETE fr
            """)
    void deleteWantsToBeFriendWithRelation(@Param(Params.PROFILE_ID) String profileId,
                                           @Param(Params.FRIEND_ID) String friendId);

    @Query("""
            MATCH(p:Profile)-[fr:FOLLOWS]->(f:Profile)
            WHERE p.profileId = $profileId and f.profileId = $toFollow
            DELETE fr
            """)
    void deleteFollowsRelation(@Param(Params.PROFILE_ID) String profileId,
                               @Param(Params.TO_FOLLOW_ID) String toFollowId);

    //TODO: Review the queries below
    @Query(
            value = """
                    MATCH (p:Profile)-[:FRIENDS_WITH]-(f:Profile)
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
    Page<FriendData> findFriendsByProfileId(@Param(Params.PROFILE_ID) String profileId, Pageable pageable);

    @Query(
            value = """
                    MATCH (p:Profile)-[:FRIENDS_WITH]-(f:Profile)
                    WHERE p.profileId = $profileId and p.isOnline = true
                    RETURN f
                    SKIP $skip LIMIT $limit
                    """,
            countQuery = """
                    MATCH (p:Profile)-[:FRIENDS_WITH]-(f:Profile)
                    WHERE p.profileId = $profileId and p.isOnline = true
                    RETURN COUNT(f)
                    """
    )
    Page<Profile> findOnlineFriendsByProfileId(String profileId, Pageable pageable);

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
    Page<String> findFollowedIdsByProfileId(@Param(Params.PROFILE_ID) String profileId, Pageable pageable);

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
    Page<FriendSuggestion> findFriendRequestsByProfileId(@Param(Params.PROFILE_ID) String profileId,
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
    Page<FriendSuggestion> findProfileSuggestions(@Param(Params.PROFILE_ID) String profileId,
                                                  Pageable pageable);

    @Query("""
            MATCH (p:Profile)<-[:FOLLOWS]-(f:Profile)
            WHERE p.profileId = $profileId
            RETURN COUNT(f)
            """)
    int getFollowersCount(@Param(Params.PROFILE_ID) String profileId);

    @Query("""
            MATCH (p:Profile)<-[:FRIENDS_WITH]-(f:Profile)
            WHERE p.profileId = $profileId
            RETURN COUNT(f)
            """)
    int getFriendsCount(@Param(Params.PROFILE_ID) String profileId);

}
