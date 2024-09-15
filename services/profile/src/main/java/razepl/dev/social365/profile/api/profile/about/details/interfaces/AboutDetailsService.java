package razepl.dev.social365.profile.api.profile.about.details.interfaces;

import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.DateOfBirthRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.RelationshipStatusRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.config.auth.User;

public interface AboutDetailsService {

    ProfileRequest updateProfileGender(User user, GenderRequest genderRequest);

    ProfileRequest deleteProfileGender(String profileId);

    ProfileRequest updateProfileDateOfBirth(User user, DateOfBirthRequest dateOfBirthRequest);

    ProfileRequest updateProfileRelationshipStatus(User user, RelationshipStatusRequest relationshipStatusRequest);

    ProfileRequest deleteProfileRelationshipStatus(String profileId);

    ProfileRequest deleteProfileCurrentCity(String profileId);

    ProfileRequest deleteProfileHomeTown(String profileId);

    ProfileRequest updateProfileCurrentCity(User user, AboutDetailsRequest cityRequest);

    ProfileRequest updateProfileHomeTown(User user, AboutDetailsRequest cityRequest);

}
