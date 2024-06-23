package razepl.dev.social365.profile.api.profile.about.contact;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.profile.about.contact.interfaces.AboutContactService;
import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.exceptions.MobileNotFoundException;
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.nodes.about.mail.Email;
import razepl.dev.social365.profile.nodes.about.mail.interfaces.EmailRepository;
import razepl.dev.social365.profile.nodes.about.mobile.Mobile;
import razepl.dev.social365.profile.nodes.about.mobile.interfaces.MobileRepository;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AboutContactServiceImpl implements AboutContactService {

    private final EmailRepository emailRepository;
    private final ProfileRepository profileRepository;
    private final MobileRepository mobileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public final ProfileRequest updateProfilePhoneNumber(AboutDetailsRequest phoneNumberRequest) {
        log.info("Updating profile phone number with request: {}", phoneNumberRequest);

        Profile profile = profileRepository.findByProfileId(phoneNumberRequest.profileId())
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Profile for updating phone number: {}", profile);

        Mobile mobile = profile.getPhoneNumber();

        if (mobile == null) {
            log.info("Mobile not found for updating, creating new one");

            mobile = Mobile
                    .builder()
                    .phoneNumber(phoneNumberRequest.detailsValue())
                    .privacyLevel(phoneNumberRequest.privacyLevel())
                    .build();

            mobile = mobileRepository.save(mobile);

            log.info("Created mobile: {}", mobile);

        } else {
            log.info("Mobile found for updating, updating existing one");

            mobile.setPhoneNumber(phoneNumberRequest.detailsValue());
            mobile.setPrivacyLevel(phoneNumberRequest.privacyLevel());

            mobile = mobileRepository.save(mobile);

            log.info("Updated mobile: {}", mobile);
        }
        profile.setPhoneNumber(mobile);

        profile = profileRepository.save(profile);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest updateProfileEmailPrivacyLevel(String profileId, PrivacyLevel privacyLevel) {
        log.info("Updating profile email privacy level with profileId: {} and privacyLevel: {}", profileId, privacyLevel);

        Profile profile = profileRepository.findByProfileId(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Profile for updating email privacy level: {}", profile);

        Email email = profile.getEmail();

        log.info("Email for updating: {}", email);

        email.setPrivacyLevel(privacyLevel);

        email = emailRepository.save(email);

        log.info("Updated mail: {}", email);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfilePhoneNumber(String profileId) {
        log.info("Deleting profile phone number with profileId: {}", profileId);

        Profile profile = profileRepository.findByProfileId(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Profile for deleting phone number: {}", profile);

        Mobile mobile = mobileRepository.findByProfileId(profileId)
                .orElseThrow(() -> new MobileNotFoundException(profile.getProfileId()));

        log.info("Mobile for deleting: {}", mobile);

        mobileRepository.delete(mobile);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

}
