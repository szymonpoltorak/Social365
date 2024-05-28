package razepl.dev.social365.profile.nodes.about.birthdate;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.api.profile.constants.Params;

import java.util.Optional;

@Repository
public interface BirthDateRepository extends Neo4jRepository<BirthDate, String> {
    @Query("""
            MATCH (p:Profile)-[:BORN_ON]->(b:BirthDate)
            WHERE p.profileId = $profileId
            RETURN b
            """)
    Optional<BirthDate> findByProfileId(@Param(Params.PROFILE_ID) String profileId);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (b:BirthDate {birthDateId: $birthDateId})
            CREATE (p)-[:BORN_ON]->(b)
            """)
    void createBornOnRelation(@Param(Params.PROFILE_ID) String profileId,
                              @Param(Params.BIRTHDATE_ID) String birthDateId);
}
