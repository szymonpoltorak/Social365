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
                    match (b:DateOfBirth)<-[:BORN_ON]-(f:Profile)-[:FRIENDS_WITH]-(p:Profile)
                    where p.profileId = $profileId
                        and date().day = date(b.dateOfBirth).day and date().month = date(b.dateOfBirth).month
                    return distinct b as birthDate, f.profileId as friendId
                    SKIP $skip LIMIT $limit
                    """,
            countQuery = """
                    match (b:DateOfBirth)<-[:BORN_ON]-(f:Profile)-[:FRIENDS_WITH]-(p:Profile)
                    where p.profileId = $profileId
                        and date().day = date(b.dateOfBirth).day and date().month = date(b.dateOfBirth).month
                    return count(b)
                    """
    )
    Page<BirthdayData> findTodayBirthdaysByProfileId(@Param(Params.PROFILE_ID) String profileId, Pageable pageable);

}
