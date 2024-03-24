package razepl.dev.social365.profile.nodes.about.mail;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

@Data
@Builder
@Node("Email")
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

    @Id
    @GeneratedValue(generatorRef = "uuid", generatorClass = GeneratedValue.UUIDGenerator.class)
    private String mailId;

    @Email(message = "Invalid email")
    private String email;

    private PrivacyLevel privacyLevel;

}
