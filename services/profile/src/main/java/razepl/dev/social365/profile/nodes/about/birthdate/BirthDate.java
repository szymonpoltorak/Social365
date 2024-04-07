package razepl.dev.social365.profile.nodes.about.birthdate;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;
import razepl.dev.social365.profile.nodes.profile.Profile;

@Data
@Builder
@Node("DateOfBirth")
@NoArgsConstructor
@AllArgsConstructor
public class BirthDate {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String birthDateId;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String dateOfBirth;

    private PrivacyLevel privacyLevel;

    @Relationship(type = "BORN_ON", direction = Relationship.Direction.INCOMING)
    private Profile profile;

}
