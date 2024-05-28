package razepl.dev.social365.profile.api.profile.about.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.profile.api.profile.about.contact.constants.AboutContactMappings;
import razepl.dev.social365.profile.api.profile.about.contact.interfaces.AboutContactController;
import razepl.dev.social365.profile.api.profile.about.contact.interfaces.AboutContactService;
import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.constants.Params;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = AboutContactMappings.ABOUT_CONTACT_API_MAPPING)
public class AboutContactControllerImpl implements AboutContactController {

    private final AboutContactService aboutContactService;

    @Override
    @PutMapping(value = AboutContactMappings.UPDATE_PROFILE_PHONE_NUMBER_MAPPING)
    public final ProfileRequest updateProfilePhoneNumber(@RequestBody AboutDetailsRequest phoneNumberRequest) {
        return aboutContactService.updateProfilePhoneNumber(phoneNumberRequest);
    }

    @Override
    @PutMapping(value = AboutContactMappings.UPDATE_PROFILE_EMAIL_PRIVACY_LEVEL_MAPPING)
    public final ProfileRequest updateProfileEmailPrivacyLevel(@RequestParam(Params.PROFILE_ID) String profileId,
                                                               @RequestParam(Params.PRIVACY_LEVEL) PrivacyLevel privacyLevel) {
        return aboutContactService.updateProfileEmailPrivacyLevel(profileId, privacyLevel);
    }

    @Override
    @DeleteMapping(value = AboutContactMappings.DELETE_PROFILE_PHONE_NUMBER_MAPPING)
    public final ProfileRequest deleteProfilePhoneNumber(@RequestParam(Params.PROFILE_ID) String profileId) {
        return aboutContactService.deleteProfilePhoneNumber(profileId);
    }

}
