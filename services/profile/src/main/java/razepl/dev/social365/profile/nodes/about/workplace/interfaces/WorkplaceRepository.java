package razepl.dev.social365.profile.nodes.about.workplace.interfaces;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.api.profile.constants.Params;
import razepl.dev.social365.profile.nodes.about.workplace.Workplace;

import java.util.Optional;

@Repository
public interface WorkplaceRepository extends Neo4jRepository<Workplace, String> {

    @Query("""
            MATCH (p:Profile)-[:WORKS_AT]->(w:Workplace)
            WHERE p.profileId = $profileId
            RETURN w
            """)
    Optional<Workplace> findWorkplaceByProfileId(@Param(Params.PROFILE_ID) String profileId);

}
