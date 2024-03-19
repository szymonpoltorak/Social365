package razepl.dev.social365.profile.nodes.profile.interfaces;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.nodes.profile.Profile;

@Repository
public interface ProfileRepository extends Neo4jRepository<Profile, String> {
}
