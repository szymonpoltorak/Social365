package razepl.dev.social365.profile.nodes.profile.interfaces;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.nodes.profile.Profile;

import java.util.Optional;

@Repository
public interface ProfileRepository extends Neo4jRepository<Profile, String> {

    @Query("MATCH (p:Profile) WHERE p.profileId = $profileId RETURN p")
    Optional<Profile> findByProfileId(String profileId);

}
