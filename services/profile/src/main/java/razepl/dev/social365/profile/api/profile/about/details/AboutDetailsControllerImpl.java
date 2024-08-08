package razepl.dev.social365.profile.api.profile.about.details;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.profile.api.profile.about.details.constants.AboutDetailsMappings;
import razepl.dev.social365.profile.api.profile.about.details.interfaces.AboutDetailsController;
import razepl.dev.social365.profile.api.profile.about.details.interfaces.AboutDetailsService;
import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.DateOfBirthRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.RelationshipStatusRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.config.AuthUser;
import razepl.dev.social365.profile.config.User;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = AboutDetailsMappings.ABOUT_DETAILS_MAPPING)
public class AboutDetailsControllerImpl implements AboutDetailsController {

    private final AboutDetailsService aboutDetailsService;

    @Override
    @PutMapping(value = AboutDetailsMappings.UPDATE_PROFILE_DATE_OF_BIRTH_MAPPING)
    public final ProfileRequest updateProfileDateOfBirth(@AuthUser User user,
                                                         @RequestBody DateOfBirthRequest dateOfBirthRequest) {
        return aboutDetailsService.updateProfileDateOfBirth(user, dateOfBirthRequest);
    }

    @Override
    @PutMapping(value = AboutDetailsMappings.UPDATE_PROFILE_GENDER_MAPPING)
    public final ProfileRequest updateProfileGender(@AuthUser User user,
                                                    @RequestBody GenderRequest genderRequest) {
        return aboutDetailsService.updateProfileGender(user, genderRequest);
    }

    @Override
    @DeleteMapping(value = AboutDetailsMappings.DELETE_PROFILE_GENDER_MAPPING)
    public final ProfileRequest deleteProfileGender(@AuthUser User user) {
        return aboutDetailsService.deleteProfileGender(user.profileId());
    }

    @Override
    @PutMapping(value = AboutDetailsMappings.UPDATE_PROFILE_RELATIONSHIP_STATUS_MAPPING)
    public final ProfileRequest updateProfileRelationshipStatus(@AuthUser User user,
                                                                @RequestBody RelationshipStatusRequest relationshipStatusRequest) {
        return aboutDetailsService.updateProfileRelationshipStatus(user, relationshipStatusRequest);
    }

    @Override
    @DeleteMapping(value = AboutDetailsMappings.DELETE_PROFILE_RELATIONSHIP_STATUS_MAPPING)
    public final ProfileRequest deleteProfileRelationshipStatus(@AuthUser User user) {
        return aboutDetailsService.deleteProfileRelationshipStatus(user.profileId());
    }

    @Override
    @PutMapping(value = AboutDetailsMappings.UPDATE_PROFILE_CURRENT_CITY_MAPPING)
    public final ProfileRequest updateProfileCurrentCity(@AuthUser User user,
                                                         @RequestBody AboutDetailsRequest cityRequest) {
        return aboutDetailsService.updateProfileCurrentCity(user, cityRequest);
    }

    @Override
    @PutMapping(value = AboutDetailsMappings.UPDATE_PROFILE_HOME_TOWN_MAPPING)
    public final ProfileRequest updateProfileHomeTown(@AuthUser User user,
                                                      @RequestBody AboutDetailsRequest cityRequest) {
        return aboutDetailsService.updateProfileHomeTown(user, cityRequest);
    }

    @Override
    @DeleteMapping(value = AboutDetailsMappings.DELETE_PROFILE_CURRENT_CITY_MAPPING)
    public final ProfileRequest deleteProfileCurrentCity(@AuthUser User user) {
        return aboutDetailsService.deleteProfileCurrentCity(user.profileId());
    }

    @Override
    @DeleteMapping(value = AboutDetailsMappings.DELETE_PROFILE_HOME_TOWN_MAPPING)
    public final ProfileRequest deleteProfileHomeTown(@AuthUser User user) {
        return aboutDetailsService.deleteProfileHomeTown(user.profileId());
    }

}
