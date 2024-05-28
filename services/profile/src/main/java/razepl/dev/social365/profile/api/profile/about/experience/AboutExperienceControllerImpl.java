package razepl.dev.social365.profile.api.profile.about.experience;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.profile.api.profile.about.experience.constants.ProfileExperienceMappings;
import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.WorkPlaceRequest;
import razepl.dev.social365.profile.api.profile.about.experience.interfaces.AboutExperienceController;
import razepl.dev.social365.profile.api.profile.about.experience.interfaces.AboutExperienceService;
import razepl.dev.social365.profile.api.profile.constants.Params;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProfileExperienceMappings.PROFILE_EXPERIENCE_API_MAPPING)
public class AboutExperienceControllerImpl implements AboutExperienceController {

    private final AboutExperienceService aboutExperienceService;

    @Override
    @PutMapping(value = ProfileExperienceMappings.UPDATE_PROFILE_WORK_PLACE_MAPPING)
    public final ProfileRequest updateProfileWorkPlace(@RequestBody WorkPlaceRequest workPlaceRequest) {
        return aboutExperienceService.updateProfileWorkPlace(workPlaceRequest);
    }

    @Override
    @PutMapping(value = ProfileExperienceMappings.UPDATE_PROFILE_EDUCATION_MAPPING)
    public final ProfileRequest updateProfileCollege(@RequestBody AboutDetailsRequest educationRequest) {
        return aboutExperienceService.updateProfileCollege(educationRequest);
    }

    @Override
    @PutMapping(value = ProfileExperienceMappings.UPDATE_PROFILE_HIGH_SCHOOL_MAPPING)
    public final ProfileRequest updateProfileHighSchool(@RequestBody AboutDetailsRequest highSchoolRequest) {
        return aboutExperienceService.updateProfileHighSchool(highSchoolRequest);
    }

    @Override
    @DeleteMapping(value = ProfileExperienceMappings.DELETE_PROFILE_HIGH_SCHOOL_MAPPING)
    public final ProfileRequest deleteProfileHighSchool(@RequestParam(Params.PROFILE_ID) String profileId) {
        return aboutExperienceService.deleteProfileHighSchool(profileId);
    }

    @Override
    @DeleteMapping(value = ProfileExperienceMappings.DELETE_PROFILE_WORK_PLACE_MAPPING)
    public final ProfileRequest deleteProfileWorkPlace(@RequestParam(Params.PROFILE_ID) String profileId) {
        return aboutExperienceService.deleteProfileWorkPlace(profileId);
    }

    @Override
    @DeleteMapping(value = ProfileExperienceMappings.DELETE_PROFILE_EDUCATION_MAPPING)
    public final ProfileRequest deleteProfileCollege(@RequestParam(Params.PROFILE_ID) String profileId) {
        return aboutExperienceService.deleteProfileCollege(profileId);
    }

}
