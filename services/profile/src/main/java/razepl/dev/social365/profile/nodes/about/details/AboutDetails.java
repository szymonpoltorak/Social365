package razepl.dev.social365.profile.nodes.about.details;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

import java.util.UUID;

@Data
@Builder
@Node("AboutDetails")
@NoArgsConstructor
@AllArgsConstructor
public class AboutDetails {

    @Id
    @GeneratedValue
    private UUID aboutDetailsId;

    @Pattern(regexp = "^[a-zA-Z0-9ąćęłńóśźżĄĆĘŁŃÓŚŹŻ]*$", message = "Property name can only contain letters and numbers")
    private String propertyValue;

    private PrivacyLevel privacyLevel;

}