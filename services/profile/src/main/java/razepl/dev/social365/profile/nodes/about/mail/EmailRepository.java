package razepl.dev.social365.profile.nodes.about.mail;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends Neo4jRepository<Email, String> {
}
