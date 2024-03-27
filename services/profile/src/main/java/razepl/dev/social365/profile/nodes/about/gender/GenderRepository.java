package razepl.dev.social365.profile.nodes.about.gender;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends Neo4jRepository<Gender, String> {
}
