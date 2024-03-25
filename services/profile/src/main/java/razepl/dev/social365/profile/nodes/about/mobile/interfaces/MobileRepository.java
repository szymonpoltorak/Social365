package razepl.dev.social365.profile.nodes.about.mobile.interfaces;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.nodes.about.mobile.Mobile;

@Repository
public interface MobileRepository extends Neo4jRepository<Mobile, String> {
}
