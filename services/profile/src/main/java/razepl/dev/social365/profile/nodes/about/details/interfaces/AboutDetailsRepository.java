package razepl.dev.social365.profile.nodes.about.details.interfaces;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;

@Repository
public interface AboutDetailsRepository extends Neo4jRepository<AboutDetails, String> {
}
