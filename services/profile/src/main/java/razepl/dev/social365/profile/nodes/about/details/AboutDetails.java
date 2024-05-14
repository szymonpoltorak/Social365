package razepl.dev.social365.profile.nodes.about.details;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;
import razepl.dev.social365.profile.nodes.about.details.enums.DetailsType;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

@Data
@Builder
@Node("AboutDetails")
@NoArgsConstructor
@AllArgsConstructor
public class AboutDetails {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String aboutDetailsId;

    @Pattern(regexp = "^[a-zA-Z0-9ąćęłńóśźżĄĆĘŁŃÓŚŹŻ]*$", message = "Property firstName can only contain letters and numbers")
    private String propertyValue;

    private PrivacyLevel privacyLevel;

    private DetailsType detailsType;

    @Version
    private long version;

}
