package razepl.dev.social365.profile.nodes.about.birthdate;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirthDateRepository extends Neo4jRepository<BirthDate, String> {
}
