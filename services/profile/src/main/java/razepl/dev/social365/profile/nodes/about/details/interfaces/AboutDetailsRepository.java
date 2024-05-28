package razepl.dev.social365.profile.nodes.about.details.interfaces;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.api.profile.constants.Params;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;

import java.util.Optional;

@Repository
public interface AboutDetailsRepository extends Neo4jRepository<AboutDetails, String> {

    @Query("""
            MATCH (p:Profile)-[:LIVES_IN]->(c:AboutDetails)
            WHERE p.profileId = $profileId
            RETURN c
            """)
    Optional<AboutDetails> findCurrentCityByProfileId(@Param(Params.PROFILE_ID) String profileId);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (c:AboutDetails {aboutDetailsId: aboutDetailsId})
            CREATE (p)-[:LIVES_IN]->(c)
            """)
    void createLivesInRelation(@Param(Params.PROFILE_ID) String profileId,
                               @Param(Params.ABOUT_DETAILS_ID) String aboutDetailsId);

    @Query("""
            MATCH (p:Profile)-[:FROM]->(c:AboutDetails)
            WHERE p.profileId = $profileId
            RETURN c
            """)
    Optional<AboutDetails> findHomeTownByProfileId(@Param(Params.PROFILE_ID) String profileId);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (h:AboutDetails {aboutDetailsId: aboutDetailsId})
            CREATE (p)-[:FROM]->(h)
            """)
    void createFromRelation(@Param(Params.PROFILE_ID) String profileId,
                            @Param(Params.ABOUT_DETAILS_ID) String aboutDetailsId);

    @Query("""
            MATCH (p:Profile)-[:WENT_TO]->(h:AboutDetails)
            WHERE p.profileId = $profileId
            RETURN h
            """)
    Optional<AboutDetails> findHighSchoolByProfileId(@Param(Params.PROFILE_ID) String profileId);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (h:AboutDetails {aboutDetailsId: aboutDetailsId})
            CREATE (p)-[:WENT_TO]->(h)
            """)
    void createHighSchoolWentToRelationship(@Param(Params.ABOUT_DETAILS_ID) String aboutDetailsId,
                                            @Param(Params.PROFILE_ID) String profileId);

    @Query("""
            MATCH (p:Profile)-STUDIED_AT->(c:AboutDetails)
            WHERE p.profileId = $profileId
            RETURN c
            """)
    Optional<AboutDetails> findCollegeByProfileId(@Param(Params.PROFILE_ID) String profileId);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (h:AboutDetails {aboutDetailsId: aboutDetailsId})
            CREATE (p)-[:STUDIED_AT]->(h)
            """)
    void createCollegeStudiedAtRelationship(@Param(Params.ABOUT_DETAILS_ID) String aboutDetailsId,
                                            @Param(Params.PROFILE_ID) String profileId);
}
