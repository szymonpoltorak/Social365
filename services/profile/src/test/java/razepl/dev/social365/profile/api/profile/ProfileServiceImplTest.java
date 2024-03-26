package razepl.dev.social365.profile.api.profile;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.exceptions.TooYoungForAccountException;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDateRepository;
import razepl.dev.social365.profile.nodes.about.mail.interfaces.MailRepository;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ProfileRepository.class, BirthDateRepository.class, ProfileMapper.class})
class ProfileServiceImplTest {

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private BirthDateRepository birthDateRepository;

    @Mock
    private MailRepository mailRepository;

    @Mock
    private ProfileMapper profileMapper;

    @Test
    final void test_getProfileSummary_throwsException() {
        // given
        String profileId = "1234";

        // when
        when(profileRepository.findById(profileId))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProfileNotFoundException.class, () -> profileService.getProfileSummary(profileId));
    }

    @Test
    final void test_getPostProfileInfo_throwsException() {
        // given
        String profileId = "1234";

        // when
        when(profileRepository.findById(profileId))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProfileNotFoundException.class, () -> profileService.getPostProfileInfo(profileId));
    }

    @Test
    final void test_getProfileSummary_shouldReturnData() {
        // given
        String profileId = "1234";
        Profile profile = new Profile();
        ProfileSummaryResponse expected = ProfileSummaryResponse.builder().build();

        // when
        when(profileRepository.findById(profileId))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToProfileSummaryResponse(profile))
                .thenReturn(expected);

        ProfileSummaryResponse actual = profileService.getProfileSummary(profileId);

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
        when(profileRepository.findById(profileId))
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

        // when
        when(profileRepository.findById(profileId))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProfileNotFoundException.class, () -> profileService.getBasicProfileInfo(profileId));
    }

    @Test
    final void test_getBasicProfileInfo_shouldReturnData() {
        // given
        String profileId = "1234";
        Profile profile = new Profile();
        ProfileResponse expected = ProfileResponse.builder().build();

        // when
        when(profileRepository.findById(profileId))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToProfileResponse(profile))
                .thenReturn(expected);

        ProfileResponse actual = profileService.getBasicProfileInfo(profileId);

        // then
        assertEquals(expected, actual, "Should return profile response");
    }

    @Test
    final void test_createUsersProfile_shouldThrowException() {
        // given
        ProfileRequest profileRequest = ProfileRequest.builder().dateOfBirth(LocalDate.now()).build();

        // when

        // then
        assertThrows(TooYoungForAccountException.class, () -> profileService.createUsersProfile(profileRequest));
    }

    @Test
    final void test_createUsersProfile_shouldReturnData() {
        // given
        ProfileRequest profileRequest = ProfileRequest.builder().dateOfBirth(LocalDate.now().minusYears(20)).build();
        Profile profile = new Profile();
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
