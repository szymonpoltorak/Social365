package razepl.dev.social365.profile.nodes.about.relationship.interfaces;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.profile.nodes.about.relationship.RelationshipStatus;

@Repository
public interface RelationshipStatusRepository extends Neo4jRepository<RelationshipStatus, String> {
}
