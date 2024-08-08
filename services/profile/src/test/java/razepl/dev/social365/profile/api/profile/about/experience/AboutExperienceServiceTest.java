package razepl.dev.social365.profile.api.profile.about.experience;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.WorkPlaceRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.config.User;
import razepl.dev.social365.profile.exceptions.ProfileDetailsNotFoundException;
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;
import razepl.dev.social365.profile.nodes.about.details.enums.DetailsType;
import razepl.dev.social365.profile.nodes.about.details.interfaces.AboutDetailsRepository;
import razepl.dev.social365.profile.nodes.about.workplace.Workplace;
import razepl.dev.social365.profile.nodes.about.workplace.interfaces.WorkplaceRepository;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        ProfileRepository.class,
        WorkplaceRepository.class,
        AboutDetailsRepository.class,
        ProfileMapper.class
})
class AboutExperienceServiceTest {

    @InjectMocks
    private AboutExperienceServiceImpl aboutExperienceService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private WorkplaceRepository workplaceRepository;

    @Mock
    private AboutDetailsRepository aboutDetailsRepository;

    @Mock
    private ProfileMapper profileMapper;

    @Test
    final void test_updateProfileWorkPlace_profileNotFound() {
        // given
        WorkPlaceRequest workPlaceRequest = WorkPlaceRequest
                .builder()
                .build();
        User user = User.builder().profileId("profileId").build();

        // when
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutExperienceService.updateProfileWorkPlace(user, workPlaceRequest);
        });
    }

    @Test
    final void test_updateProfileWorkPlace_addNewWorkplace() {
        // given
        String profileId = "profileId";
        User user = User.builder().profileId(profileId).build();
        WorkPlaceRequest workPlaceRequest = WorkPlaceRequest
                .builder()
                .position("position")
                .privacyLevel(PrivacyLevel.FRIENDS)
                .workplace("workplace")
                .build();
        Workplace workPlace = Workplace
                .builder()
                .position("position")
                .privacyLevel(PrivacyLevel.FRIENDS)
                .workplaceName("workplace")
                .build();
        Profile profile = new Profile();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.of(profile));
        when(workplaceRepository.save(workPlace))
                .thenReturn(workPlace);
        when(profileRepository.save(profile))
                .thenReturn(profile);
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutExperienceService.updateProfileWorkPlace(user, workPlaceRequest);

        // then
        Assertions.assertEquals(expected, actual);
        verify(workplaceRepository).save(workPlace);
    }

    @Test
    final void test_updateProfileHighSchool_profileNotFound() {
        // given
        String profileId = "profileId";
        AboutDetailsRequest highSchoolRequest = AboutDetailsRequest
                .builder()
                .build();
        User user = User.builder().profileId(profileId).build();

        // when
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutExperienceService.updateProfileHighSchool(user, highSchoolRequest);
        });
    }

    @Test
    final void test_updateProfileHighSchool_addNewHighSchool() {
        // given
        String profileId = "profileId";
        AboutDetailsRequest highSchoolRequest = AboutDetailsRequest
                .builder()
                .detailsValue("details")
                .detailsType(DetailsType.HIGH_SCHOOL)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        Profile profile = Profile
                .builder()
                .profileId(profileId)
                .highSchool(AboutDetails.builder().detailsType(DetailsType.HIGH_SCHOOL).build())
                .build();
        AboutDetails highSchool = AboutDetails
                .builder()
                .propertyValue("details")
                .detailsType(DetailsType.HIGH_SCHOOL)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        User user = User.builder().profileId(profileId).build();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.of(profile));
        when(aboutDetailsRepository.save(highSchool))
                .thenReturn(highSchool);
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutExperienceService.updateProfileHighSchool(user, highSchoolRequest);

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    final void test_deleteProfileHighSchool_profileNotFound() {
        // given
        String profileId = "profileId";

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutExperienceService.deleteProfileHighSchool(profileId);
        });
    }

    @Test
    final void test_deleteProfileHighSchool_deleteHighSchool() {
        // given
        String profileId = "profileId";
        Profile profile = Profile
                .builder()
                .profileId(profileId)
                .highSchool(AboutDetails.builder().detailsType(DetailsType.HIGH_SCHOOL).build())
                .build();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(aboutDetailsRepository.findHighSchoolByProfileId(profileId))
                .thenReturn(Optional.of(profile.getHighSchool()));
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutExperienceService.deleteProfileHighSchool(profileId);

        // then
        Assertions.assertEquals(expected, actual);
        verify(aboutDetailsRepository).delete(profile.getHighSchool());
    }

    @Test
    final void test_deleteProfileHighSchool_highSchoolIsNull() {
        // given
        String profileId = "profileId";
        Profile profile = new Profile();
        profile.setHighSchool(null);

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));

        // then
        Assertions.assertThrows(ProfileDetailsNotFoundException.class, () -> {
            aboutExperienceService.deleteProfileHighSchool(profileId);
        });
    }

    @Test
    final void test_deleteProfileWorkPlace_profileNotFound() {
        // given
        String profileId = "profileId";

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutExperienceService.deleteProfileWorkPlace(profileId);
        });
    }

    @Test
    final void test_deleteProfileWorkPlace_deleteWorkPlace() {
        // given
        String profileId = "profileId";
        Profile profile = Profile
                .builder()
                .profileId(profileId)
                .workplace(Workplace.builder().build())
                .build();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(workplaceRepository.findWorkplaceByProfileId(profileId))
                .thenReturn(Optional.of(profile.getWorkplace()));
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutExperienceService.deleteProfileWorkPlace(profileId);

        // then
        Assertions.assertEquals(expected, actual);
        verify(workplaceRepository).delete(profile.getWorkplace());
    }

    @Test
    final void test_deleteProfileCollege_profileNotFound() {
        // given
        String profileId = "profileId";

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutExperienceService.deleteProfileCollege(profileId);
        });
    }

    @Test
    final void test_deleteProfileCollege_deleteCollege() {
        // given
        String profileId = "profileId";
        Profile profile = Profile
                .builder()
                .profileId(profileId)
                .college(AboutDetails.builder().detailsType(DetailsType.COLLEGE).build())
                .build();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(aboutDetailsRepository.findCollegeByProfileId(profileId))
                .thenReturn(Optional.of(profile.getCollege()));
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutExperienceService.deleteProfileCollege(profileId);

        // then
        Assertions.assertEquals(expected, actual);
        verify(aboutDetailsRepository).delete(profile.getCollege());
    }

}
