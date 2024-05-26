package razepl.dev.social365.profile.nodes.about.details.interfaces;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;

import java.util.Optional;

@Repository
public interface AboutDetailsRepository extends Neo4jRepository<AboutDetails, String> {

    @Query("""
            MATCH (p:Profile)-[:LIVES_IN]->(c:AboutDetails)
            WHERE p.profileId = $profileId
            RETURN c
            """)
    Optional<AboutDetails> findCurrentCityByProfileId(String profileId);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (c:AboutDetails {aboutDetailsId: aboutDetailsId})
            CREATE (p)-[:LIVES_IN]->(c)
            """)
    void createLivesInRelation(String profileId, String aboutDetailsId);

    @Query("""
            MATCH (p:Profile)-[:FROM]->(c:AboutDetails)
            WHERE p.profileId = $profileId
            RETURN c
            """)
    Optional<AboutDetails> findHomeTownByProfileId(String profileId);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (h:AboutDetails {aboutDetailsId: aboutDetailsId})
            CREATE (p)-[:FROM]->(h)
            """)
    void createFromRelation(String profileId, String aboutDetailsId);
}
