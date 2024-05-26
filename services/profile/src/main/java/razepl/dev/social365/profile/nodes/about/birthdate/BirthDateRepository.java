package razepl.dev.social365.profile.nodes.about.birthdate;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BirthDateRepository extends Neo4jRepository<BirthDate, String> {
    Optional<BirthDate> findByProfileProfileId(String profileId);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (b:BirthDate {birthDateId: $birthDateId})
            CREATE (p)-[:BORN_ON]->(b)
            """)
    void createBornOnRelation(String profileId, String birthDateId);
}
