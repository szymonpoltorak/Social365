package razepl.dev.social365.profile.api.profile.about.contact.interfaces;

import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

public interface AboutContactController {

    ProfileRequest updateProfilePhoneNumber(AboutDetailsRequest phoneNumberRequest);

    ProfileRequest updateProfileEmailPrivacyLevel(String profileId, PrivacyLevel privacyLevel);

    ProfileRequest deleteProfilePhoneNumber(String profileId);

}