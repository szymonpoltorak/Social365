package razepl.dev.social365.profile.api.profile.about;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.profile.about.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.data.DateOfBirthRequest;
import razepl.dev.social365.profile.api.profile.about.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.about.data.RelationshipStatusRequest;
import razepl.dev.social365.profile.api.profile.about.data.WorkPlaceRequest;
import razepl.dev.social365.profile.api.profile.about.interfaces.ProfileAboutService;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileAboutServiceImpl implements ProfileAboutService {

    @Override
    public final ProfileRequest updateProfileBio(String username, String bio) {
        return null;
    }

    @Override
    public final ProfileRequest updateProfileGender(GenderRequest genderRequest) {
        return null;
    }

    @Override
    public final ProfileRequest updateProfileCurrentCity(AboutDetailsRequest cityRequest) {
        return null;
    }

    @Override
    public final ProfileRequest updateProfileHomeTown(AboutDetailsRequest cityRequest) {
        return null;
    }

    @Override
    public final ProfileRequest updateProfileWorkPlace(WorkPlaceRequest workPlaceRequest) {
        return null;
    }

    @Override
    public final ProfileRequest updateProfileEducation(AboutDetailsRequest educationRequest) {
        return null;
    }

    @Override
    public final ProfileRequest updateProfileRelationshipStatus(RelationshipStatusRequest relationshipStatusRequest) {
        return null;
    }

    @Override
    public final ProfileRequest updateProfileDateOfBirth(DateOfBirthRequest dateOfBirthRequest) {
        return null;
    }

    @Override
    public final ProfileRequest updateProfilePhoneNumber(AboutDetailsRequest phoneNumberRequest) {
        return null;
    }

    @Override
    public final ProfileRequest updateProfileEmailPrivacyLevel(String username, PrivacyLevel privacyLevel) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileGender(String username) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileCurrentCity(String username) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileHomeTown(String username) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileWorkPlace(String username) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileEducation(String username) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileRelationshipStatus(String username) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfilePhoneNumber(String username) {
        return null;
    }

}
