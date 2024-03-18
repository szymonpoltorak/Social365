package razepl.dev.social365.profile.nodes.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import razepl.dev.social365.profile.nodes.profile.enums.Gender;
import razepl.dev.social365.profile.nodes.profile.enums.RelationshipStatus;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@Node("Profile")
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue
    private UUID profileId;

    private String description;

    private String name;

    private String lastName;

    private String username;

    private String jobTitle;

    private String college;

    private String highSchool;

    private RelationshipStatus relationshipStatus;

    private Gender gender;

    private String currentCity;

    private String homeTown;

    private String phoneNumber;

    @Relationship(type = "FRIENDS_WITH", direction = Relationship.Direction.OUTGOING)
    private Set<Profile> friends;
}
