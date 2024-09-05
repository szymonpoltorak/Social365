package razepl.dev.social365.profile.api.profile.about.experience;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.profile.api.profile.about.experience.constants.ProfileExperienceMappings;
import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.WorkPlaceRequest;
import razepl.dev.social365.profile.api.profile.about.experience.interfaces.AboutExperienceController;
import razepl.dev.social365.profile.api.profile.about.experience.interfaces.AboutExperienceService;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.config.auth.AuthUser;
import razepl.dev.social365.profile.config.auth.User;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProfileExperienceMappings.PROFILE_EXPERIENCE_API_MAPPING)
public class AboutExperienceControllerImpl implements AboutExperienceController {

    private final AboutExperienceService aboutExperienceService;

    @Override
    @PutMapping(value = ProfileExperienceMappings.UPDATE_PROFILE_WORK_PLACE_MAPPING)
    public final ProfileRequest updateProfileWorkPlace(@AuthUser User user, @RequestBody WorkPlaceRequest workPlaceRequest) {
        return aboutExperienceService.updateProfileWorkPlace(user, workPlaceRequest);
    }

    @Override
    @PutMapping(value = ProfileExperienceMappings.UPDATE_PROFILE_EDUCATION_MAPPING)
    public final ProfileRequest updateProfileCollege(@AuthUser User user, @RequestBody AboutDetailsRequest educationRequest) {
        return aboutExperienceService.updateProfileCollege(user, educationRequest);
    }

    @Override
    @PutMapping(value = ProfileExperienceMappings.UPDATE_PROFILE_HIGH_SCHOOL_MAPPING)
    public final ProfileRequest updateProfileHighSchool(@AuthUser User user, @RequestBody AboutDetailsRequest highSchoolRequest) {
        return aboutExperienceService.updateProfileHighSchool(user, highSchoolRequest);
    }

    @Override
    @DeleteMapping(value = ProfileExperienceMappings.DELETE_PROFILE_HIGH_SCHOOL_MAPPING)
    public final ProfileRequest deleteProfileHighSchool(@AuthUser User user) {
        return aboutExperienceService.deleteProfileHighSchool(user.profileId());
    }

    @Override
    @DeleteMapping(value = ProfileExperienceMappings.DELETE_PROFILE_WORK_PLACE_MAPPING)
    public final ProfileRequest deleteProfileWorkPlace(@AuthUser User user) {
        return aboutExperienceService.deleteProfileWorkPlace(user.profileId());
    }

    @Override
    @DeleteMapping(value = ProfileExperienceMappings.DELETE_PROFILE_EDUCATION_MAPPING)
    public final ProfileRequest deleteProfileCollege(@AuthUser User user) {
        return aboutExperienceService.deleteProfileCollege(user.profileId());
    }

}
