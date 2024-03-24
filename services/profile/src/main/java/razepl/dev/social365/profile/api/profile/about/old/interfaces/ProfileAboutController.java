package razepl.dev.social365.profile.api.profile.about.old.interfaces;

import razepl.dev.social365.profile.api.profile.about.old.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.old.data.DateOfBirthRequest;
import razepl.dev.social365.profile.api.profile.about.old.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.about.old.data.RelationshipStatusRequest;
import razepl.dev.social365.profile.api.profile.about.old.data.WorkPlaceRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

public interface ProfileAboutController {

    ProfileRequest updateProfileCurrentCity(AboutDetailsRequest cityRequest);

    ProfileRequest updateProfileHomeTown(AboutDetailsRequest cityRequest);

    ProfileRequest updateProfileWorkPlace(WorkPlaceRequest workPlaceRequest);

    ProfileRequest updateProfileEducation(AboutDetailsRequest educationRequest);

    ProfileRequest updateProfileRelationshipStatus(RelationshipStatusRequest relationshipStatusRequest);

    ProfileRequest updateProfileDateOfBirth(DateOfBirthRequest dateOfBirthRequest);

    ProfileRequest updateProfilePhoneNumber(AboutDetailsRequest phoneNumberRequest);

    ProfileRequest updateProfileEmailPrivacyLevel(String profileId, PrivacyLevel privacyLevel);

    ProfileRequest deleteProfileCurrentCity(String profileId);

    ProfileRequest deleteProfileHomeTown(String profileId);

    ProfileRequest deleteProfileWorkPlace(String profileId);

    ProfileRequest deleteProfileEducation(String profileId);

    ProfileRequest deleteProfileRelationshipStatus(String profileId);

    ProfileRequest deleteProfilePhoneNumber(String profileId);

}
