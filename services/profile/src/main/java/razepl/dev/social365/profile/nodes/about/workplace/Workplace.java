package razepl.dev.social365.profile.nodes.about.workplace;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;
import razepl.dev.social365.profile.nodes.constants.ValidationPatterns;

@Data
@Builder
@Node("Workplace")
@NoArgsConstructor
@AllArgsConstructor
public class Workplace {

    @Id
    private String workplaceId;

    @Pattern(regexp = ValidationPatterns.NAME_PATTERN, message = "Position can only contain letters and numbers")
    @Size(max = 100, message = "Position must be less than 100 characters")
    private String position;

    @Pattern(regexp = ValidationPatterns.NAME_PATTERN, message = "Workplace can only contain letters and numbers")
    @Size(max = 100, message = "Workplace name must be less than 100 characters")
    private String workplaceName;

    private PrivacyLevel privacyLevel;

}
