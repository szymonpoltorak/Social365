package razepl.dev.social365.profile.api.profile.about.old;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.profile.about.old.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.old.data.DateOfBirthRequest;
import razepl.dev.social365.profile.api.profile.about.old.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.about.old.data.RelationshipStatusRequest;
import razepl.dev.social365.profile.api.profile.about.old.data.WorkPlaceRequest;
import razepl.dev.social365.profile.api.profile.about.old.interfaces.ProfileAboutService;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.nodes.about.gender.Gender;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileAboutServiceImpl implements ProfileAboutService {

    private static final String PROFILE_FOUND = "Profile found: {}";
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public final ProfileRequest updateProfileCurrentCity(AboutDetailsRequest cityRequest) {
        log.info("Updating profile current city with data: {}", cityRequest);

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
    public final ProfileRequest updateProfileEmailPrivacyLevel(String profileId, PrivacyLevel privacyLevel) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileCurrentCity(String profileId) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileHomeTown(String profileId) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileWorkPlace(String profileId) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileEducation(String profileId) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileRelationshipStatus(String profileId) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfilePhoneNumber(String profileId) {
        return null;
    }

}
