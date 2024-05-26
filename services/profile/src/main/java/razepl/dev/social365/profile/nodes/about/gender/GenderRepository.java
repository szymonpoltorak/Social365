package razepl.dev.social365.profile.nodes.about.gender;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenderRepository extends Neo4jRepository<Gender, String> {

    @Query("""
            match(p:Profile)-[:IS]->(g:Gender)
            where p.profileId = $profileId
            return g
            """)
    Optional<Gender> findByProfileId(String profileId);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (g:Gender {genderId: genderId})
            CREATE (p)-[:IS]->(b)
            """)
    void createIsGenderRelation(String profileId, String genderId);

}
