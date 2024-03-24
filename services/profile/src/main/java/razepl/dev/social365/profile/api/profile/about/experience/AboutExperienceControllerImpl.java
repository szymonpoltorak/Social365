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
    public final ProfileRequest updateProfileEducation(@RequestBody AboutDetailsRequest educationRequest) {
        return aboutExperienceService.updateProfileEducation(educationRequest);
    }

    @Override
    @DeleteMapping(value = ProfileExperienceMappings.DELETE_PROFILE_WORK_PLACE_MAPPING)
    public final ProfileRequest deleteProfileWorkPlace(@RequestParam String profileId) {
        return aboutExperienceService.deleteProfileWorkPlace(profileId);
    }

    @Override
    @DeleteMapping(value = ProfileExperienceMappings.DELETE_PROFILE_EDUCATION_MAPPING)
    public final ProfileRequest deleteProfileEducation(@RequestParam String profileId) {
        return aboutExperienceService.deleteProfileEducation(profileId);
    }

}
