package razepl.dev.social365.profile.nodes.about.mobile.interfaces;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.api.profile.constants.Params;
import razepl.dev.social365.profile.nodes.about.mobile.Mobile;

import java.util.Optional;

@Repository
public interface MobileRepository extends Neo4jRepository<Mobile, String> {

    @Query("""
            MATCH (p:Profile)-[:HAS]->(m:Mobile)
            WHERE p.profileId = $profileId
            RETURN m
            """)
    Optional<Mobile> findByProfileId(@Param(Params.PROFILE_ID) String profileId);

    @Query("""
            MATCH (m:Mobile {mobileId: $mobileId})
            MATCH (p:Profile {profileId: $profileId})
            CREATE (m)<-[:HAS]-(p)
            """)
    void createMobileHasRelationship(@Param(Params.MOBILE_ID) String mobileId, @Param(Params.PROFILE_ID) String profileId);

}
