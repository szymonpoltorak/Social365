package razepl.dev.social365.profile.nodes.about.birthdate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.api.profile.constants.Params;
import razepl.dev.social365.profile.api.profile.data.BirthdayData;

import java.util.Optional;

@Repository
public interface BirthDateRepository extends Neo4jRepository<BirthDate, String> {
    @Query("""
            MATCH (p:Profile)-[:BORN_ON]->(b:BirthDate)
            WHERE p.profileId = $profileId
            RETURN b
            """)
    Optional<BirthDate> findByProfileId(@Param(Params.PROFILE_ID) String profileId);

    @Query(
            value = """
                    MATCH (p:Profile)-[:FRIENDS_WITH]->(f:Profile)-[:BORN_ON]->(b:BirthDate)
                    WHERE p.profileId = $profileId and date().day = date(b.dateOfBirth).day and date().month = date(b.dateOfBirth).month
                    RETURN f as friend, b as birthDate
                    SKIP $skip LIMIT $limit
                    """,
            countQuery = """
                    MATCH (p:Profile)-[:FRIENDS_WITH]->(f:Profile)-[:BORN_ON]->(b:BirthDate)
                    WHERE p.profileId = $profileId and date().day = date(b.dateOfBirth).day and date().month = date(b.dateOfBirth).month
                    RETURN count(b)
                    """
    )
    Page<BirthdayData> findTodayBirthdaysByProfileId(@Param(Params.PROFILE_ID) String profileId, Pageable pageable);

    @Query("""
            MATCH (p:Profile {profileId: $profileId})
            MATCH (b:BirthDate {birthDateId: $birthDateId})
            CREATE (p)-[:BORN_ON]->(b)
            """)
    void createBornOnRelation(@Param(Params.PROFILE_ID) String profileId,
                              @Param(Params.BIRTHDATE_ID) String birthDateId);
}
