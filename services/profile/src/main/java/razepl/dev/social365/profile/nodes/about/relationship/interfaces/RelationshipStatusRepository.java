package razepl.dev.social365.profile.nodes.about.relationship.interfaces;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.api.profile.constants.Params;
import razepl.dev.social365.profile.nodes.about.relationship.RelationshipStatus;

import java.util.Optional;

@Repository
public interface RelationshipStatusRepository extends Neo4jRepository<RelationshipStatus, String> {

    @Query("""
            MATCH (p:Profile)-[:IS]->(r:RelationshipStatus)
            WHERE p.profileId = $profileId
            RETURN r
            """)
    Optional<RelationshipStatus> findByProfileId(@Param(Params.PROFILE_ID) String profileId);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (r:RelationshipStatus {relationshipStatusId: $relationshipStatusId})
            CREATE (p)-[:IS]->(r)
            """)
    void createIsRelationshipStatusRelation(@Param(Params.PROFILE_ID) String profileId,
                                            @Param(Params.RELATIONSHIP_STATUS_ID) String relationshipStatusId);
}
