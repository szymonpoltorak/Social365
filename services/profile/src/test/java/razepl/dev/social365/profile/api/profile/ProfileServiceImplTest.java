package razepl.dev.social365.profile.api.profile;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;
import razepl.dev.social365.profile.config.auth.User;
import razepl.dev.social365.profile.utils.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDate;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDateRepository;
import razepl.dev.social365.profile.nodes.about.mail.Email;
import razepl.dev.social365.profile.nodes.about.mail.interfaces.EmailRepository;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;
import razepl.dev.social365.profile.utils.interfaces.ParamValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        ProfileRepository.class, BirthDateRepository.class, EmailRepository.class, ProfileMapper.class
})
class ProfileServiceImplTest {

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private BirthDateRepository birthDateRepository;

    @Mock
    private EmailRepository mailRepository;

    @Mock
    private ProfileMapper profileMapper;

    @Mock
    private ParamValidator paramValidator;

    @Test
    final void test_getProfileSummary_throwsException() {
        // given
        String profileId = "1234";
        User user = User.builder().profileId(profileId).build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProfileNotFoundException.class, () -> profileService.getProfileSummary(user));
    }

    @Test
    final void test_getPostProfileInfo_throwsException() {
        // given
        String profileId = "1234";

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProfileNotFoundException.class, () -> profileService.getPostProfileInfo(profileId));
    }

    @Test
    final void test_getProfileSummary_shouldReturnData() {
        // given
        String profileId = "1234";
        Profile profile = Profile.builder().profileId(profileId).build();
        ProfileSummaryResponse expected = ProfileSummaryResponse.builder().build();
        User user = User.builder().profileId(profileId).build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToProfileSummaryResponse(profile))
                .thenReturn(expected);

        ProfileSummaryResponse actual = profileService.getProfileSummary(user);

        // then
        assertEquals(expected, actual, "Should return profile summary");
    }

    @Test
    final void test_getPostProfileInfo_shouldReturnData() {
        // given
        String profileId = "1234";
        Profile profile = new Profile();
        ProfilePostResponse expected = ProfilePostResponse.builder().build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToProfilePostResponse(profile))
                .thenReturn(expected);

        ProfilePostResponse actual = profileService.getPostProfileInfo(profileId);

        // then
        assertEquals(expected, actual, "Should return profile response");
    }

    @Test
    final void test_getBasicProfileInfo_throwsException() {
        // given
        String profileId = "1234";
        User user = User.builder().profileId(profileId).build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProfileNotFoundException.class, () -> profileService.getBasicProfileInfo(user));
    }

    @Test
    final void test_getBasicProfileInfo_shouldReturnData() {
        // given
        String profileId = "1234";
        Profile profile = new Profile();
        ProfileResponse expected = ProfileResponse.builder().build();
        User user = User.builder().profileId(profileId).build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToProfileResponse(profile))
                .thenReturn(expected);

        ProfileResponse actual = profileService.getBasicProfileInfo(user);

        // then
        assertEquals(expected, actual, "Should return profile response");
    }

    @Test
    final void test_createUsersProfile_shouldReturnData() {
        // given
        ProfileRequest profileRequest = ProfileRequest
                .builder()
                .dateOfBirth(LocalDate.now().minusYears(20))
                .build();
        Profile profile = Profile
                .builder()
                .profilePictureId(1L)
                .email(Email.builder().privacyLevel(PrivacyLevel.ONLY_ME).build())
                .bannerPictureId(-1L)
                .isOnline(true)
                .birthDate(BirthDate
                        .builder()
                        .dateOfBirth(profileRequest.dateOfBirth().format(DateTimeFormatter.ISO_LOCAL_DATE))
                        .privacyLevel(PrivacyLevel.ONLY_ME)
                        .build()
                )
                .build();
        ProfileResponse expected = ProfileResponse.builder().build();

        // when
        when(birthDateRepository.save(profile.getBirthDate()))
                .thenReturn(profile.getBirthDate());
        when(mailRepository.save(profile.getEmail()))
                .thenReturn(profile.getEmail());
        when(profileRepository.save(profile))
                .thenReturn(profile);
        when(profileMapper.mapProfileToProfileResponse(profile))
                .thenReturn(expected);

        ProfileResponse actual = profileService.createUsersProfile(profileRequest);

        // then
        assertEquals(expected, actual, "Should return profile response");
    }
}
