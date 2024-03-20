package razepl.dev.social365.profile.api.profile.about;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.profile.api.profile.about.constants.ProfileAboutMappings;
import razepl.dev.social365.profile.api.profile.about.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.data.DateOfBirthRequest;
import razepl.dev.social365.profile.api.profile.about.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.about.data.RelationshipStatusRequest;
import razepl.dev.social365.profile.api.profile.about.data.WorkPlaceRequest;
import razepl.dev.social365.profile.api.profile.about.interfaces.ProfileAboutController;
import razepl.dev.social365.profile.api.profile.about.interfaces.ProfileAboutService;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProfileAboutMappings.PROFILE_ABOUT_API_MAPPING)
public class ProfileAboutControllerImpl implements ProfileAboutController {

    private final ProfileAboutService profileAboutService;

    @Override
    @PostMapping(value = ProfileAboutMappings.UPDATE_PROFILE_BIO_MAPPING)
    public final ProfileRequest updateProfileBio(@RequestParam String username, @RequestParam String bio) {
        return profileAboutService.updateProfileBio(username, bio);
    }

    @Override
    @PutMapping(value = ProfileAboutMappings.UPDATE_PROFILE_GENDER_MAPPING)
    public final ProfileRequest updateProfileGender(@RequestBody GenderRequest genderRequest) {
        return profileAboutService.updateProfileGender(genderRequest);
    }

    @Override
    @PutMapping(value = ProfileAboutMappings.UPDATE_PROFILE_CURRENT_CITY_MAPPING)
    public final ProfileRequest updateProfileCurrentCity(@RequestBody AboutDetailsRequest cityRequest) {
        return profileAboutService.updateProfileCurrentCity(cityRequest);
    }

    @Override
    @PutMapping(value = ProfileAboutMappings.UPDATE_PROFILE_HOME_TOWN_MAPPING)
    public final ProfileRequest updateProfileHomeTown(@RequestBody AboutDetailsRequest cityRequest) {
        return profileAboutService.updateProfileHomeTown(cityRequest);
    }

    @Override
    @PutMapping(value = ProfileAboutMappings.UPDATE_PROFILE_WORK_PLACE_MAPPING)
    public final ProfileRequest updateProfileWorkPlace(@RequestBody WorkPlaceRequest workPlaceRequest) {
        return profileAboutService.updateProfileWorkPlace(workPlaceRequest);
    }

    @Override
    @PutMapping(value = ProfileAboutMappings.UPDATE_PROFILE_EDUCATION_MAPPING)
    public final ProfileRequest updateProfileEducation(@RequestBody AboutDetailsRequest educationRequest) {
        return profileAboutService.updateProfileEducation(educationRequest);
    }

    @Override
    @PutMapping(value = ProfileAboutMappings.UPDATE_PROFILE_RELATIONSHIP_STATUS_MAPPING)
    public final ProfileRequest updateProfileRelationshipStatus(@RequestBody RelationshipStatusRequest relationshipStatusRequest) {
        return profileAboutService.updateProfileRelationshipStatus(relationshipStatusRequest);
    }

    @Override
    @PutMapping(value = ProfileAboutMappings.UPDATE_PROFILE_DATE_OF_BIRTH_MAPPING)
    public final ProfileRequest updateProfileDateOfBirth(@RequestBody DateOfBirthRequest dateOfBirthRequest) {
        return profileAboutService.updateProfileDateOfBirth(dateOfBirthRequest);
    }

    @Override
    @PutMapping(value = ProfileAboutMappings.UPDATE_PROFILE_PHONE_NUMBER_MAPPING)
    public final ProfileRequest updateProfilePhoneNumber(@RequestBody AboutDetailsRequest phoneNumberRequest) {
        return profileAboutService.updateProfilePhoneNumber(phoneNumberRequest);
    }

    @Override
    @PutMapping(value = ProfileAboutMappings.UPDATE_PROFILE_EMAIL_PRIVACY_LEVEL_MAPPING)
    public final ProfileRequest updateProfileEmailPrivacyLevel(@RequestParam String username,
                                                               @RequestParam PrivacyLevel privacyLevel) {
        return profileAboutService.updateProfileEmailPrivacyLevel(username, privacyLevel);
    }

    @Override
    @DeleteMapping(value = ProfileAboutMappings.DELETE_PROFILE_GENDER_MAPPING)
    public final ProfileRequest deleteProfileGender(@RequestParam String username) {
        return profileAboutService.deleteProfileGender(username);
    }

    @Override
    @DeleteMapping(value = ProfileAboutMappings.DELETE_PROFILE_CURRENT_CITY_MAPPING)
    public final ProfileRequest deleteProfileCurrentCity(@RequestParam String username) {
        return profileAboutService.deleteProfileCurrentCity(username);
    }

    @Override
    @DeleteMapping(value = ProfileAboutMappings.DELETE_PROFILE_HOME_TOWN_MAPPING)
    public final ProfileRequest deleteProfileHomeTown(@RequestParam String username) {
        return profileAboutService.deleteProfileHomeTown(username);
    }

    @Override
    @DeleteMapping(value = ProfileAboutMappings.DELETE_PROFILE_WORK_PLACE_MAPPING)
    public final ProfileRequest deleteProfileWorkPlace(@RequestParam String username) {
        return profileAboutService.deleteProfileWorkPlace(username);
    }

    @Override
    @DeleteMapping(value = ProfileAboutMappings.DELETE_PROFILE_EDUCATION_MAPPING)
    public final ProfileRequest deleteProfileEducation(@RequestParam String username) {
        return profileAboutService.deleteProfileEducation(username);
    }

    @Override
    @DeleteMapping(value = ProfileAboutMappings.DELETE_PROFILE_RELATIONSHIP_STATUS_MAPPING)
    public final ProfileRequest deleteProfileRelationshipStatus(@RequestParam String username) {
        return profileAboutService.deleteProfileRelationshipStatus(username);
    }

    @Override
    @DeleteMapping(value = ProfileAboutMappings.DELETE_PROFILE_PHONE_NUMBER_MAPPING)
    public final ProfileRequest deleteProfilePhoneNumber(@RequestParam String username) {
        return profileAboutService.deleteProfilePhoneNumber(username);
    }

}
