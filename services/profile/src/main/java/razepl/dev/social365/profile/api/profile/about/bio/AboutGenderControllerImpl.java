package razepl.dev.social365.profile.api.profile.about.bio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.profile.api.profile.about.bio.constants.AboutGenderMappings;
import razepl.dev.social365.profile.api.profile.about.bio.interfaces.AboutGenderController;
import razepl.dev.social365.profile.api.profile.about.bio.interfaces.AboutGenderService;
import razepl.dev.social365.profile.api.profile.about.old.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.constants.ProfileMappings;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = AboutGenderMappings.ABOUT_GENDER_MAPPING)
public class AboutGenderControllerImpl implements AboutGenderController {

    private final AboutGenderService aboutGenderService;

    @Override
    @PutMapping(value = AboutGenderMappings.UPDATE_PROFILE_GENDER_MAPPING)
    public final ProfileRequest updateProfileGender(@RequestBody GenderRequest genderRequest) {
        return aboutGenderService.updateProfileGender(genderRequest);
    }

    @Override
    @DeleteMapping(value = AboutGenderMappings.DELETE_PROFILE_GENDER_MAPPING)
    public final ProfileRequest deleteProfileGender(@RequestParam(ProfileMappings.PROFILE_ID) String profileId) {
        return aboutGenderService.deleteProfileGender(profileId);
    }

}
