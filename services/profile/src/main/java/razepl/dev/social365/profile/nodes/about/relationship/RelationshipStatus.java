package razepl.dev.social365.profile.nodes.about.relationship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;
import razepl.dev.social365.profile.nodes.profile.enums.RelationshipStatusType;

@Data
@Builder
@Node("RelationshipStatus")
@AllArgsConstructor
@NoArgsConstructor
public class RelationshipStatus {

    @Id
    private String relationshipStatusId;

    private RelationshipStatusType relationshipStatus;

    private PrivacyLevel privacyLevel;

}
