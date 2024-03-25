package razepl.dev.social365.profile.api.profile.about.contact;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.profile.about.contact.interfaces.AboutContactService;
import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.nodes.about.mail.Mail;
import razepl.dev.social365.profile.nodes.about.mail.MailRepository;
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

    private final MailRepository mailRepository;
    private final ProfileRepository profileRepository;
    private final MobileRepository mobileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public final ProfileRequest updateProfilePhoneNumber(AboutDetailsRequest phoneNumberRequest) {
        log.info("Updating profile phone number with request: {}", phoneNumberRequest);

        Profile profile = profileRepository.findById(phoneNumberRequest.profileId())
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Profile for updating phone number: {}", profile);

        Mobile mobile = profile.getPhoneNumber();

        mobile.setPhoneNumber(phoneNumberRequest.detailsValue());
        mobile.setPrivacyLevel(phoneNumberRequest.privacyLevel());

        mobile = mobileRepository.save(mobile);

        log.info("Updated mobile: {}", mobile);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest updateProfileEmailPrivacyLevel(String profileId, PrivacyLevel privacyLevel) {
        log.info("Updating profile email privacy level with profileId: {} and privacyLevel: {}", profileId, privacyLevel);

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Profile for updating email privacy level: {}", profile);

        Mail mail = profile.getMail();

        mail.setPrivacyLevel(privacyLevel);

        mail = mailRepository.save(mail);

        log.info("Updated mail: {}", mail);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfilePhoneNumber(String profileId) {
        log.info("Deleting profile phone number with profileId: {}", profileId);

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Profile for deleting phone number: {}", profile);

        Mobile mobile = profile.getPhoneNumber();

        mobileRepository.delete(mobile);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

}
