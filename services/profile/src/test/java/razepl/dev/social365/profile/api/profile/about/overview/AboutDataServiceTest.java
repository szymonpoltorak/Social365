package razepl.dev.social365.profile.api.profile.about.overview;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import razepl.dev.social365.profile.api.profile.about.overview.data.ContactInfoResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.LocationsResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.OverviewResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.WorkEducationResponse;
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        ProfileRepository.class,
        ProfileMapper.class
})
class AboutDataServiceTest {

    @InjectMocks
    private AboutDataServiceImpl aboutDataService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProfileMapper profileMapper;

    @Test
    final void test_getOverview_profileNotFound() {
        // given
        String profileId = "profileId";

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDataService.getOverview(profileId);
        });
    }

    @Test
    final void test_getOverview_success() {
        // given
        String profileId = "profileId";
        Profile profile = Profile
                .builder()
                .lastName("lastName")
                .firstName("firstName")
                .bio("bio")
                .build();
        OverviewResponse expected = OverviewResponse
                .builder()
                .build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToOverviewResponse(profile))
                .thenReturn(expected);

        OverviewResponse actual = aboutDataService.getOverview(profileId);

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    final void test_getWorkEducation_profileNotFound() {
        // given
        String profileId = "profileId";

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDataService.getWorkEducation(profileId);
        });
    }

    @Test
    final void test_getLocations_profileNotFound() {
        // given
        String profileId = "profileId";

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDataService.getLocations(profileId);
        });
    }

    @Test
    final void test_getContactInfo_profileNotFound() {
        // given
        String profileId = "profileId";

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDataService.getContactInfo(profileId);
        });
    }

    @Test
    final void test_getWorkEducation_success() {
        // given
        String profileId = "profileId";
        Profile profile = Profile
                .builder()
                .lastName("lastName")
                .firstName("firstName")
                .bio("bio")
                .build();
        WorkEducationResponse expected = WorkEducationResponse
                .builder()
                .build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToWorkEducationResponse(profile))
                .thenReturn(expected);

        WorkEducationResponse actual = aboutDataService.getWorkEducation(profileId);

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    final void test_getLocations_success() {
        // given
        String profileId = "profileId";
        Profile profile = Profile
                .builder()
                .lastName("lastName")
                .firstName("firstName")
                .bio("bio")
                .build();
        LocationsResponse expected = LocationsResponse
                .builder()
                .build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToLocationsResponse(profile))
                .thenReturn(expected);

        LocationsResponse actual = aboutDataService.getLocations(profileId);

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    final void test_getContactInfo_success() {
        // given
        String profileId = "profileId";
        Profile profile = Profile
                .builder()
                .lastName("lastName")
                .firstName("firstName")
                .bio("bio")
                .build();
        ContactInfoResponse expected = ContactInfoResponse
                .builder()
                .build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToContactInfoResponse(profile))
                .thenReturn(expected);

        ContactInfoResponse actual = aboutDataService.getContactInfo(profileId);

        // then
        Assertions.assertEquals(expected, actual);
    }

}
