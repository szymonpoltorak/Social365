package razepl.dev.social365.profile.api.profile.about.interfaces;

import razepl.dev.social365.profile.api.profile.about.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.data.DateOfBirthRequest;
import razepl.dev.social365.profile.api.profile.about.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.about.data.RelationshipStatusRequest;
import razepl.dev.social365.profile.api.profile.about.data.WorkPlaceRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

public interface ProfileAboutService {

    ProfileRequest updateProfileBio(String username, String bio);

    ProfileRequest updateProfileGender(GenderRequest genderRequest);

    ProfileRequest updateProfileCurrentCity(AboutDetailsRequest cityRequest);

    ProfileRequest updateProfileHomeTown(AboutDetailsRequest cityRequest);

    ProfileRequest updateProfileWorkPlace(WorkPlaceRequest workPlaceRequest);

    ProfileRequest updateProfileEducation(AboutDetailsRequest educationRequest);

    ProfileRequest updateProfileRelationshipStatus(RelationshipStatusRequest relationshipStatusRequest);

    ProfileRequest updateProfileDateOfBirth(DateOfBirthRequest dateOfBirthRequest);

    ProfileRequest updateProfilePhoneNumber(AboutDetailsRequest phoneNumberRequest);

    ProfileRequest updateProfileEmailPrivacyLevel(String username, PrivacyLevel privacyLevel);

    ProfileRequest deleteProfileGender(String username);

    ProfileRequest deleteProfileCurrentCity(String username);

    ProfileRequest deleteProfileHomeTown(String username);

    ProfileRequest deleteProfileWorkPlace(String username);

    ProfileRequest deleteProfileEducation(String username);

    ProfileRequest deleteProfileRelationshipStatus(String username);

    ProfileRequest deleteProfilePhoneNumber(String username);

}
