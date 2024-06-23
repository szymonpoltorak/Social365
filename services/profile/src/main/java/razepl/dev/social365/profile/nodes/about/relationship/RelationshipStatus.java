package razepl.dev.social365.profile.nodes.about.relationship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;
import razepl.dev.social365.profile.nodes.about.relationship.enums.RelationshipStatusType;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

@Data
@Builder
@Node("RelationshipStatus")
@AllArgsConstructor
@NoArgsConstructor
public class RelationshipStatus {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String relationshipStatusId;

    private RelationshipStatusType relationshipStatus;

    private PrivacyLevel privacyLevel;

    @Version
    private long version;

}
