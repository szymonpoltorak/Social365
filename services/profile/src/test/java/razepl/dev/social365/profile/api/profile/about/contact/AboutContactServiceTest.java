package razepl.dev.social365.profile.api.profile.about.contact;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.exceptions.ProfileDetailsNotFoundException;
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.nodes.about.mail.Email;
import razepl.dev.social365.profile.nodes.about.mail.interfaces.EmailRepository;
import razepl.dev.social365.profile.nodes.about.mobile.Mobile;
import razepl.dev.social365.profile.nodes.about.mobile.interfaces.MobileRepository;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        ProfileRepository.class, EmailRepository.class, MobileRepository.class, ProfileMapper.class
})
class AboutContactServiceTest {

    @InjectMocks
    private AboutContactServiceImpl aboutContactService;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private MobileRepository mobileRepository;

    @Mock
    private ProfileMapper profileMapper;

    @Test
    final void test_updateProfilePhoneNumber_addNewPhoneNumber() {
        // given
        String profileId = "profileId";
        AboutDetailsRequest phoneNumberRequest = AboutDetailsRequest
                .builder()
                .profileId(profileId)
                .privacyLevel(PrivacyLevel.ONLY_ME)
                .detailsValue("1234567890")
                .build();
        ProfileRequest expected = ProfileRequest.builder().build();
        Mobile mobile = Mobile
                .builder()
                .phoneNumber(phoneNumberRequest.detailsValue())
                .privacyLevel(phoneNumberRequest.privacyLevel())
                .build();
        Profile profile = Profile.builder().profileId(profileId).phoneNumber(mobile).build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(mobileRepository.save(mobile))
                .thenReturn(mobile);
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutContactService.updateProfilePhoneNumber(phoneNumberRequest);

        // then
        Assertions.assertEquals(expected, actual, "ProfileRequest should be equal");
    }

    @Test
    final void test_updateProfilePhoneNumber_update() {
        // given
        String profileId = "profileId";
        AboutDetailsRequest phoneNumberRequest = AboutDetailsRequest
                .builder()
                .profileId(profileId)
                .privacyLevel(PrivacyLevel.ONLY_ME)
                .detailsValue("1234567890")
                .build();
        ProfileRequest expected = ProfileRequest.builder().build();
        Mobile mobile = Mobile
                .builder()
                .phoneNumber(phoneNumberRequest.detailsValue())
                .privacyLevel(phoneNumberRequest.privacyLevel())
                .build();
        Profile profile = Profile.builder().profileId(profileId).phoneNumber(null).build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(mobileRepository.save(mobile))
                .thenReturn(mobile);
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutContactService.updateProfilePhoneNumber(phoneNumberRequest);

        // then
        Assertions.assertEquals(expected, actual, "ProfileRequest should be equal");
    }

    @Test
    final void test_updateProfilePhoneNumber_exception() {
        // given
        String profileId = "profileId";
        AboutDetailsRequest phoneNumberRequest = AboutDetailsRequest.builder().profileId(profileId).build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutContactService.updateProfilePhoneNumber(phoneNumberRequest);
        });
    }

    @Test
    final void test_updateProfileEmailPrivacyLevel_update() {
        // given
        String profileId = "profileId";
        AboutDetailsRequest phoneNumberRequest = AboutDetailsRequest
                .builder()
                .profileId(profileId)
                .privacyLevel(PrivacyLevel.ONLY_ME)
                .detailsValue("1234567890")
                .build();
        ProfileRequest expected = ProfileRequest.builder().build();
        Email email = Email
                .builder()
                .emailValue(phoneNumberRequest.detailsValue())
                .privacyLevel(phoneNumberRequest.privacyLevel())
                .build();
        Profile profile = Profile.builder().profileId(profileId).email(email).build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(emailRepository.save(email))
                .thenReturn(email);
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutContactService.updateProfileEmailPrivacyLevel(profileId, PrivacyLevel.ONLY_ME);

        // then
        Assertions.assertEquals(expected, actual, "ProfileRequest should be equal");

    }

    @Test
    final void test_updateProfileEmailPrivacyLevel_exception() {
        // given
        String profileId = "profileId";

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutContactService.updateProfileEmailPrivacyLevel(profileId, PrivacyLevel.ONLY_ME);
        });
    }

    @Test
    final void test_deleteProfilePhoneNumber_success() {
        // given
        String profileId = "profileId";
        AboutDetailsRequest phoneNumberRequest = AboutDetailsRequest
                .builder()
                .profileId(profileId)
                .privacyLevel(PrivacyLevel.ONLY_ME)
                .detailsValue("1234567890")
                .build();
        ProfileRequest expected = ProfileRequest.builder().build();
        Mobile mobile = Mobile
                .builder()
                .phoneNumber(phoneNumberRequest.detailsValue())
                .privacyLevel(phoneNumberRequest.privacyLevel())
                .build();
        Profile profile = Profile.builder().profileId(profileId).phoneNumber(mobile).build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutContactService.deleteProfilePhoneNumber(profileId);

        // then
        Assertions.assertEquals(expected, actual, "ProfileRequest should be equal");
        verify(mobileRepository).delete(mobile);
    }

    @Test
    final void test_deleteProfilePhoneNumber_nullMobile() {
        // given
        String profileId = "profileId";
        AboutDetailsRequest phoneNumberRequest = AboutDetailsRequest
                .builder()
                .profileId(profileId)
                .privacyLevel(PrivacyLevel.ONLY_ME)
                .detailsValue("1234567890")
                .build();
        Profile profile = Profile.builder().profileId(profileId).phoneNumber(null).build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));

        // then
        Assertions.assertThrows(ProfileDetailsNotFoundException.class, () -> {
            aboutContactService.deleteProfilePhoneNumber(profileId);
        });
    }

    @Test
    final void test_deleteProfilePhoneNumber_exception() {
        // given
        String profileId = "profileId";
        AboutDetailsRequest phoneNumberRequest = AboutDetailsRequest.builder().profileId(profileId).build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutContactService.updateProfilePhoneNumber(phoneNumberRequest);
        });
    }

}
