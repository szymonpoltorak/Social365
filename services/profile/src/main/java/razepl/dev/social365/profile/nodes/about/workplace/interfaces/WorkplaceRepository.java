package razepl.dev.social365.profile.nodes.about.workplace.interfaces;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.nodes.about.workplace.Workplace;

@Repository
public interface WorkplaceRepository extends Neo4jRepository<Workplace, String> {
}
