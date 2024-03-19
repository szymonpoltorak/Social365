package razepl.dev.social365.profile.nodes.about.gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import razepl.dev.social365.profile.nodes.profile.enums.GenderType;

@Data
@Builder
@Node("Gender")
@AllArgsConstructor
@NoArgsConstructor
public class Gender {

    @Id
    private String genderId;

    private GenderType genderType;

}
