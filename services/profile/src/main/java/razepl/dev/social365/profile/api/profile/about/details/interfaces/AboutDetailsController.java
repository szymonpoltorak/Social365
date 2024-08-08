package razepl.dev.social365.profile.api.profile.about.details.interfaces;

import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.DateOfBirthRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.RelationshipStatusRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.config.User;

public interface AboutDetailsController {

    ProfileRequest updateProfileGender(User user, GenderRequest genderRequest);

    ProfileRequest deleteProfileGender(User user);

    ProfileRequest updateProfileDateOfBirth(User user, DateOfBirthRequest dateOfBirthRequest);

    ProfileRequest updateProfileRelationshipStatus(User user, RelationshipStatusRequest relationshipStatusRequest);

    ProfileRequest deleteProfileRelationshipStatus(User user);

    ProfileRequest deleteProfileCurrentCity(User user);

    ProfileRequest deleteProfileHomeTown(User user);

    ProfileRequest updateProfileCurrentCity(User user, AboutDetailsRequest cityRequest);

    ProfileRequest updateProfileHomeTown(User user, AboutDetailsRequest cityRequest);

}
