package razepl.dev.social365.profile.nodes.about.mobile;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import razepl.dev.social365.profile.nodes.about.details.enums.PrivacyLevel;

@Data
@Builder
@Node("Mobile")
@AllArgsConstructor
@NoArgsConstructor
public class Mobile {

    @Id
    @Pattern(regexp = "^[\\d+]*$", message = "Phone number can only contain numbers")
    @Size(min = 3, max = 30, message = "Phone number should be at least 3 and max 30")
    private String phoneNumber;

    private PrivacyLevel privacyLevel;

}
