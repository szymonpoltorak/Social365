package razepl.dev.social365.profile.nodes.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;
import razepl.dev.social365.profile.nodes.about.gender.Gender;
import razepl.dev.social365.profile.nodes.about.mobile.Mobile;
import razepl.dev.social365.profile.nodes.about.relationship.RelationshipStatus;
import razepl.dev.social365.profile.nodes.profile.enums.RelationshipStatusType;

import java.util.Set;

@Data
@Builder
@Node("Profile")
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private static final String NAME_PATTERN = "^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]*$";

    @Id
    @Email(message = "Invalid email")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9ąćęłńóśźżĄĆĘŁŃÓŚŹŻ]*$", message = "Description can only contain letters and numbers")
    @Size(max = 100, message = "Description must be less than 100 characters")
    private String description;

    @Pattern(regexp = NAME_PATTERN, message = "Name can only contain letters")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String name;

    @Pattern(regexp = NAME_PATTERN, message = "Last name can only contain letters")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
    private String lastName;

    private String job;

    @Relationship(type = "IS_A", direction = Relationship.Direction.OUTGOING)
    private RelationshipStatus relationshipStatus;

    @Relationship(type = "IS", direction = Relationship.Direction.OUTGOING)
    private Gender gender;

    @Relationship(type = "HAS", direction = Relationship.Direction.OUTGOING)
    private Mobile phoneNumber;

    @Relationship(type = "STUDIED_AT", direction = Relationship.Direction.OUTGOING)
    private AboutDetails college;

    @Relationship(type = "WENT_TO", direction = Relationship.Direction.OUTGOING)
    private AboutDetails highSchool;

    @Relationship(type = "LIVES_IN", direction = Relationship.Direction.OUTGOING)
    private AboutDetails currentCity;

    @Relationship(type = "FROM", direction = Relationship.Direction.OUTGOING)
    private AboutDetails homeTown;

    @Relationship(type = "FRIENDS_WITH", direction = Relationship.Direction.OUTGOING)
    private Set<Profile> friends;

}
