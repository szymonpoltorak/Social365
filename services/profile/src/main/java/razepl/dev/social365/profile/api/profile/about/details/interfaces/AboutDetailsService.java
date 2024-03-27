package razepl.dev.social365.profile.api.profile.about.details.interfaces;

import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.DateOfBirthRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.RelationshipStatusRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;

public interface AboutDetailsService {

    ProfileRequest updateProfileGender(GenderRequest genderRequest);

    ProfileRequest deleteProfileGender(String profileId);

    ProfileRequest updateProfileDateOfBirth(DateOfBirthRequest dateOfBirthRequest);

    ProfileRequest updateProfileRelationshipStatus(RelationshipStatusRequest relationshipStatusRequest);

    ProfileRequest deleteProfileRelationshipStatus(String profileId);

    ProfileRequest deleteProfileCurrentCity(String profileId);

    ProfileRequest deleteProfileHomeTown(String profileId);

    ProfileRequest updateProfileCurrentCity(AboutDetailsRequest cityRequest);

    ProfileRequest updateProfileHomeTown(AboutDetailsRequest cityRequest);

}
