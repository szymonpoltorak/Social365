package razepl.dev.social365.profile.nodes.about.birthdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.support.DateString;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

import java.time.LocalDate;

@Data
@Builder
@Node("DateOfBirth")
@NoArgsConstructor
@AllArgsConstructor
public class BirthDate {

    @Id
    private String birthDateId;

    @DateString
    private LocalDate dateOfBirth;

    private PrivacyLevel privacyLevel;

}
