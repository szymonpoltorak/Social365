package razepl.dev.social365.profile.nodes.about.mail.interfaces;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.nodes.about.mail.Email;

import java.util.Optional;

@Repository
public interface EmailRepository extends Neo4jRepository<Email, String> {

    @Query("""
            MATCH (p:Profile)-[:HAS]->(e:Email)
            WHERE p.profileId = $profileId
            RETURN e
            """)
    Optional<Email> findEmailByProfileId(String profileId);


    @Query("""
            MATCH (e:Email {emailId: $emailId})
            MATCH (p:Profile {profileId: $profileId})
            CREATE (e)<-[:HAS]-(p)
            """)
    void createEmailHasRelation(String emailId, String profileId);

}
