package razepl.dev.social365.profile.nodes.profile;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDate;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;
import razepl.dev.social365.profile.nodes.about.gender.Gender;
import razepl.dev.social365.profile.nodes.about.mail.Email;
import razepl.dev.social365.profile.nodes.about.mobile.Mobile;
import razepl.dev.social365.profile.nodes.about.relationship.RelationshipStatus;
import razepl.dev.social365.profile.nodes.about.workplace.Workplace;
import razepl.dev.social365.profile.nodes.constants.ValidationPatterns;

@Data
@Builder
@Node("Profile")
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String profileId;

    @PositiveOrZero(message = "User id must be positive or zero")
    private long userId;

    @Pattern(regexp = ValidationPatterns.NAME_PATTERN, message = "Description can only contain letters and numbers")
    @Size(max = 100, message = "Description must be less than 100 characters")
    private String bio;

    @Pattern(regexp = ValidationPatterns.NAME_PATTERN, message = "Name can only contain letters")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String firstName;

    @Pattern(regexp = ValidationPatterns.NAME_PATTERN, message = "Last firstName can only contain letters")
    @Size(min = 2, max = 30, message = "Last firstName must be between 2 and 30 characters")
    private String lastName;

    @PositiveOrZero(message = "Profile picture id must be positive or zero")
    private long profilePictureId;

    //TODO: Add updating if user is online
    private boolean isOnline;

    @Version
    private long version;

    @Relationship(type = "HAS", direction = Relationship.Direction.OUTGOING)
    private Email email;

    @Relationship(type = "BORN_ON", direction = Relationship.Direction.OUTGOING)
    private BirthDate birthDate;

    @Relationship(type = "WORKS_AS", direction = Relationship.Direction.OUTGOING)
    private Workplace workplace;

    @Relationship(type = "IS", direction = Relationship.Direction.OUTGOING)
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

    final String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
