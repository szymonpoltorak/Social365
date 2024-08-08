package razepl.dev.social365.profile.api.profile.about.contact.interfaces;

import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.config.User;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

public interface AboutContactController {

    ProfileRequest updateProfilePhoneNumber(User user, AboutDetailsRequest phoneNumberRequest);

    ProfileRequest updateProfileEmailPrivacyLevel(User user, PrivacyLevel privacyLevel);

    ProfileRequest deleteProfilePhoneNumber(User user);

}
