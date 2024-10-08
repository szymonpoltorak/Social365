package razepl.dev.social365.profile.nodes.about.mobile;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

@Data
@Builder
@Node("Mobile")
@AllArgsConstructor
@NoArgsConstructor
public class Mobile {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String mobileId;

    @Pattern(regexp = "^[\\d+]*$", message = "Phone number can only contain numbers")
    @Size(min = 3, max = 30, message = "Phone number should be at least 3 and max 30")
    private String phoneNumber;

    private PrivacyLevel privacyLevel;

    @Version
    private long version;

}
