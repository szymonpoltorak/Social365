package razepl.dev.social365.profile.api.profile.about.contact.interfaces;

import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

public interface AboutContactService {

    ProfileRequest updateProfilePhoneNumber(AboutDetailsRequest phoneNumberRequest);

    ProfileRequest updateProfileEmailPrivacyLevel(String username, PrivacyLevel privacyLevel);

    ProfileRequest deleteProfilePhoneNumber(String username);

}
