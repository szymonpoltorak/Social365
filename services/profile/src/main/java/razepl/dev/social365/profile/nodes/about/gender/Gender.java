package razepl.dev.social365.profile.nodes.about.gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;
import razepl.dev.social365.profile.nodes.about.gender.enums.GenderType;
import razepl.dev.social365.profile.nodes.profile.Profile;

@Data
@Builder
@Node("Gender")
@AllArgsConstructor
@NoArgsConstructor
public class Gender {

    @Id
    
    private String genderId;

    private GenderType genderType;

    private PrivacyLevel privacyLevel;

    @Relationship(type = "IS", direction = Relationship.Direction.INCOMING)
    private Profile profile;
}
