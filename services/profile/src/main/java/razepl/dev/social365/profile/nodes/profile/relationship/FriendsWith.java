package razepl.dev.social365.profile.nodes.profile.relationship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import razepl.dev.social365.profile.nodes.profile.Profile;

@Data
@Builder
@RelationshipProperties
@NoArgsConstructor
@AllArgsConstructor
public class FriendsWith {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private Profile friend;
}
