package razepl.dev.social365.profile.api.profile.about.details;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.profile.api.profile.about.details.constants.AboutDetailsMappings;
import razepl.dev.social365.profile.api.profile.about.details.interfaces.AboutDetailsController;
import razepl.dev.social365.profile.api.profile.about.details.interfaces.AboutDetailsService;
import razepl.dev.social365.profile.api.profile.about.experience.constants.ProfileExperienceMappings;
import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.DateOfBirthRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.RelationshipStatusRequest;
import razepl.dev.social365.profile.api.profile.constants.Params;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = AboutDetailsMappings.ABOUT_DETAILS_MAPPING)
public class AboutDetailsControllerImpl implements AboutDetailsController {

    private final AboutDetailsService aboutDetailsService;

    @Override
    @PutMapping(value = AboutDetailsMappings.UPDATE_PROFILE_DATE_OF_BIRTH_MAPPING)
    public final ProfileRequest updateProfileDateOfBirth(@RequestBody DateOfBirthRequest dateOfBirthRequest) {
        return aboutDetailsService.updateProfileDateOfBirth(dateOfBirthRequest);
    }

    @Override
    @PutMapping(value = AboutDetailsMappings.UPDATE_PROFILE_GENDER_MAPPING)
    public final ProfileRequest updateProfileGender(@RequestBody GenderRequest genderRequest) {
        return aboutDetailsService.updateProfileGender(genderRequest);
    }

    @Override
    @DeleteMapping(value = AboutDetailsMappings.DELETE_PROFILE_GENDER_MAPPING)
    public final ProfileRequest deleteProfileGender(@RequestParam(Params.PROFILE_ID) String profileId) {
        return aboutDetailsService.deleteProfileGender(profileId);
    }

    @Override
    @PutMapping(value = ProfileExperienceMappings.UPDATE_PROFILE_RELATIONSHIP_STATUS_MAPPING)
    public final ProfileRequest updateProfileRelationshipStatus(@RequestBody RelationshipStatusRequest relationshipStatusRequest) {
        return aboutDetailsService.updateProfileRelationshipStatus(relationshipStatusRequest);
    }

    @Override
    @DeleteMapping(value = ProfileExperienceMappings.DELETE_PROFILE_RELATIONSHIP_STATUS_MAPPING)
    public final ProfileRequest deleteProfileRelationshipStatus(@RequestParam String profileId) {
        return aboutDetailsService.deleteProfileRelationshipStatus(profileId);
    }

    @Override
    @PutMapping(value = ProfileExperienceMappings.UPDATE_PROFILE_CURRENT_CITY_MAPPING)
    public final ProfileRequest updateProfileCurrentCity(@RequestBody AboutDetailsRequest cityRequest) {
        return aboutDetailsService.updateProfileCurrentCity(cityRequest);
    }

    @Override
    @PutMapping(value = ProfileExperienceMappings.UPDATE_PROFILE_HOME_TOWN_MAPPING)
    public final ProfileRequest updateProfileHomeTown(@RequestBody AboutDetailsRequest cityRequest) {
        return aboutDetailsService.updateProfileHomeTown(cityRequest);
    }

    @Override
    @DeleteMapping(value = ProfileExperienceMappings.DELETE_PROFILE_CURRENT_CITY_MAPPING)
    public final ProfileRequest deleteProfileCurrentCity(@RequestParam String profileId) {
        return aboutDetailsService.deleteProfileCurrentCity(profileId);
    }

    @Override
    @DeleteMapping(value = ProfileExperienceMappings.DELETE_PROFILE_HOME_TOWN_MAPPING)
    public final ProfileRequest deleteProfileHomeTown(@RequestParam String profileId) {
        return aboutDetailsService.deleteProfileHomeTown(profileId);
    }

}
