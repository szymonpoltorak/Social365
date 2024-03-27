package razepl.dev.social365.profile.nodes.about.mail;

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
public class Email {

    @Id
    @GeneratedValue(generatorRef = "uuid", generatorClass = GeneratedValue.UUIDGenerator.class)
    private String mailId;

    @jakarta.validation.constraints.Email(message = "Invalid email")
    private String emailValue;

    private PrivacyLevel privacyLevel;

}
