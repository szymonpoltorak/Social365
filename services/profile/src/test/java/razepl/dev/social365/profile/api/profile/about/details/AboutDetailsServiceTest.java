package razepl.dev.social365.profile.api.profile.about.details;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.DateOfBirthRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.RelationshipStatusRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.exceptions.IllegalDetailsTypeException;
import razepl.dev.social365.profile.exceptions.ProfileDetailsNotFoundException;
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.exceptions.TooYoungForAccountException;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDate;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDateRepository;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;
import razepl.dev.social365.profile.nodes.about.details.enums.DetailsType;
import razepl.dev.social365.profile.nodes.about.details.interfaces.AboutDetailsRepository;
import razepl.dev.social365.profile.nodes.about.gender.Gender;
import razepl.dev.social365.profile.nodes.about.gender.GenderRepository;
import razepl.dev.social365.profile.nodes.about.gender.enums.GenderType;
import razepl.dev.social365.profile.nodes.about.relationship.RelationshipStatus;
import razepl.dev.social365.profile.nodes.about.relationship.enums.RelationshipStatusType;
import razepl.dev.social365.profile.nodes.about.relationship.interfaces.RelationshipStatusRepository;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {
        ProfileRepository.class, ProfileMapper.class, RelationshipStatusRepository.class,
        AboutDetailsRepository.class, GenderRepository.class, BirthDateRepository.class
})
class AboutDetailsServiceTest {

    @InjectMocks
    private AboutDetailsServiceImpl aboutDetailsService;

    @Mock
    private RelationshipStatusRepository relationshipStatusRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private AboutDetailsRepository aboutDetailsRepository;

    @Mock
    private GenderRepository genderRepository;

    @Mock
    private ProfileMapper profileMapper;

    @Mock
    private BirthDateRepository dateOfBirthRepository;

    @Test
    final void test_updateProfileGender_newGender() {
        // given
        GenderRequest genderRequest = GenderRequest
                .builder()
                .profileId("profileId")
                .gender(GenderType.MALE)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        Gender gender = Gender
                .builder()
                .genderType(genderRequest.gender())
                .privacyLevel(genderRequest.privacyLevel())
                .build();
        Profile profile = Profile
                .builder()
                .build();
        ProfileRequest expected = ProfileRequest
                .builder()
                .build();

        // when
        when(profileRepository.findByProfileId(genderRequest.profileId()))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutDetailsService.updateProfileGender(genderRequest);

        // then
        Assertions.assertEquals(expected, actual);
        verify(genderRepository).save(gender);
    }

    @Test
    final void test_updateProfileGender_profileNotFound() {
        // given
        GenderRequest genderRequest = GenderRequest
                .builder()
                .profileId("profileId")
                .gender(GenderType.MALE)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();

        // when
        when(profileRepository.findByProfileId(genderRequest.profileId()))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDetailsService.updateProfileGender(genderRequest);
        });
    }

    @Test
    final void test_deleteProfileGender_profileNotFound() {
        // given
        String profileId = "profileId";

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDetailsService.deleteProfileGender(profileId);
        });
    }

    @Test
    final void test_deleteProfileGender() {
        // given
        String profileId = "profileId";
        Profile profile = Profile
                .builder()
                .gender(new Gender())
                .build();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(ProfileRequest.builder().build());

        ProfileRequest actual = aboutDetailsService.deleteProfileGender(profileId);

        // then
        Assertions.assertEquals(expected, actual);
        verify(genderRepository).deleteById(profile.getGender().getGenderId());
    }

    @Test
    final void test_updateProfileDateOfBirth_profileNotFound() {
        // given
        DateOfBirthRequest dateOfBirthRequest = DateOfBirthRequest
                .builder()
                .profileId("profileId")
                .dateOfBirth(null)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();

        // when
        when(dateOfBirthRepository.findByProfileProfileId(dateOfBirthRequest.profileId()))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDetailsService.updateProfileDateOfBirth(dateOfBirthRequest);
        });
    }

    @Test
    final void test_updateProfileDateOfBirth_tooYoung() {
        // given
        DateOfBirthRequest dateOfBirthRequest = DateOfBirthRequest
                .builder()
                .profileId("profileId")
                .dateOfBirth(new Date())
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        Profile profile = Profile
                .builder()
                .build();
        BirthDate birthDate = BirthDate
                .builder()
                .profile(profile)
                .dateOfBirth(dateOfBirthRequest.dateOfBirth())
                .privacyLevel(dateOfBirthRequest.privacyLevel())
                .build();

        // when
        when(dateOfBirthRepository.findByProfileProfileId(dateOfBirthRequest.profileId()))
                .thenReturn(Optional.of(birthDate));

        // then
        Assertions.assertThrows(TooYoungForAccountException.class, () -> {
            aboutDetailsService.updateProfileDateOfBirth(dateOfBirthRequest);
        });
    }

    @Test
    final void test_updateProfileDateOfBirth_illegalDate() {
        // given
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        DateOfBirthRequest dateOfBirthRequest = DateOfBirthRequest
                .builder()
                .profileId("profileId")
                .dateOfBirth(calendar.getTime())
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        Profile profile = Profile
                .builder()
                .build();
        BirthDate birthDate = BirthDate
                .builder()
                .profile(profile)
                .dateOfBirth(dateOfBirthRequest.dateOfBirth())
                .privacyLevel(dateOfBirthRequest.privacyLevel())
                .build();

        // when
        when(dateOfBirthRepository.findByProfileProfileId(dateOfBirthRequest.profileId()))
                .thenReturn(Optional.of(birthDate));

        // then
        Assertions.assertThrows(TooYoungForAccountException.class, () -> {
            aboutDetailsService.updateProfileDateOfBirth(dateOfBirthRequest);
        });
    }

    @Test
    final void test_updateProfileDateOfBirth_success() {
        // given
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -20);
        DateOfBirthRequest dateOfBirthRequest = DateOfBirthRequest
                .builder()
                .profileId("profileId")
                .dateOfBirth(calendar.getTime())
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        Profile profile = Profile
                .builder()
                .build();
        BirthDate birthDate = BirthDate
                .builder()
                .profile(profile)
                .dateOfBirth(dateOfBirthRequest.dateOfBirth())
                .privacyLevel(dateOfBirthRequest.privacyLevel())
                .build();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(dateOfBirthRepository.findByProfileProfileId(dateOfBirthRequest.profileId()))
                .thenReturn(Optional.of(birthDate));
        when(dateOfBirthRepository.save(birthDate))
                .thenReturn(birthDate);
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutDetailsService.updateProfileDateOfBirth(dateOfBirthRequest);

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    final void test_updateProfileRelationshipStatus_profileNotFound() {
        // given
        RelationshipStatusRequest relationshipStatusRequest = RelationshipStatusRequest
                .builder()
                .profileId("profileId")
                .relationshipStatus(RelationshipStatusType.SINGLE)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();

        // when
        when(profileRepository.findByProfileId(relationshipStatusRequest.profileId()))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDetailsService.updateProfileRelationshipStatus(relationshipStatusRequest);
        });
    }

    @Test
    final void test_updateProfileRelationshipStatus_success() {
        // given
        RelationshipStatusRequest relationshipStatusRequest = RelationshipStatusRequest
                .builder()
                .profileId("profileId")
                .relationshipStatus(RelationshipStatusType.SINGLE)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        Profile profile = Profile
                .builder()
                .relationshipStatus(new RelationshipStatus())
                .build();
        RelationshipStatus status = profile.getRelationshipStatus();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(relationshipStatusRequest.profileId()))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutDetailsService.updateProfileRelationshipStatus(relationshipStatusRequest);

        // then
        Assertions.assertEquals(expected, actual);
        verify(relationshipStatusRepository).save(status);
    }

    @Test
    final void test_updateProfileCurrentCity_profileNotFound() {
        // given
        AboutDetailsRequest cityRequest = AboutDetailsRequest
                .builder()
                .profileId("profileId")
                .detailsValue("city")
                .detailsType(DetailsType.CURRENT_CITY)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();

        // when
        when(profileRepository.findByProfileId(cityRequest.profileId()))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDetailsService.updateProfileCurrentCity(cityRequest);
        });
    }

    @Test
    final void test_updateProfileCurrentCity_wrongDetailsType() {
        // given
        AboutDetailsRequest cityRequest = AboutDetailsRequest
                .builder()
                .profileId("profileId")
                .detailsValue("city")
                .detailsType(DetailsType.HOMETOWN)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        Profile profile = Profile
                .builder()
                .build();

        // when
        when(profileRepository.findByProfileId(cityRequest.profileId()))
                .thenReturn(Optional.of(profile));

        // then
        Assertions.assertThrows(IllegalDetailsTypeException.class, () -> {
            aboutDetailsService.updateProfileCurrentCity(cityRequest);
        });
    }

    @Test
    final void test_updateProfileCurrentCity_success() {
        // given
        AboutDetailsRequest cityRequest = AboutDetailsRequest
                .builder()
                .profileId("profileId")
                .detailsValue("city")
                .detailsType(DetailsType.CURRENT_CITY)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        Profile profile = Profile
                .builder()
                .currentCity(new AboutDetails())
                .build();
        AboutDetails currentCity = profile.getCurrentCity();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(cityRequest.profileId()))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutDetailsService.updateProfileCurrentCity(cityRequest);

        // then
        Assertions.assertEquals(expected, actual);
        verify(aboutDetailsRepository).save(currentCity);
    }

    @Test
    final void test_updateProfileHomeTown_profileNotFound() {
        // given
        AboutDetailsRequest homeRequest = AboutDetailsRequest
                .builder()
                .profileId("profileId")
                .detailsValue("city")
                .detailsType(DetailsType.HOMETOWN)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();

        // when
        when(profileRepository.findByProfileId(homeRequest.profileId()))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDetailsService.updateProfileHomeTown(homeRequest);
        });
    }

    @Test
    final void test_updateProfileHomeTown_wrongDetailsType() {
        // given
        AboutDetailsRequest homeRequest = AboutDetailsRequest
                .builder()
                .profileId("profileId")
                .detailsValue("city")
                .detailsType(DetailsType.CURRENT_CITY)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        Profile profile = Profile
                .builder()
                .build();

        // when
        when(profileRepository.findByProfileId(homeRequest.profileId()))
                .thenReturn(Optional.of(profile));

        // then
        Assertions.assertThrows(IllegalDetailsTypeException.class, () -> {
            aboutDetailsService.updateProfileHomeTown(homeRequest);
        });
    }

    @Test
    final void test_updateProfileHomeTown_success() {
        // given
        AboutDetailsRequest homeRequest = AboutDetailsRequest
                .builder()
                .profileId("profileId")
                .detailsValue("city")
                .detailsType(DetailsType.HOMETOWN)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        Profile profile = Profile
                .builder()
                .homeTown(new AboutDetails())
                .build();
        AboutDetails homeTown = profile.getHomeTown();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(homeRequest.profileId()))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutDetailsService.updateProfileHomeTown(homeRequest);

        // then
        Assertions.assertEquals(expected, actual);
        verify(aboutDetailsRepository).save(homeTown);
    }

    @Test
    final void test_deleteProfileRelationshipStatus_profileNotFound() {
        // given
        String profileId = "profileId";

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDetailsService.deleteProfileRelationshipStatus(profileId);
        });
    }

    @Test
    final void test_deleteProfileRelationshipStatus_statusIsNull() {
        // given
        String profileId = "profileId";
        Profile profile = Profile
                .builder()
                .build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));

        // then
        Assertions.assertThrows(ProfileDetailsNotFoundException.class, () -> {
            aboutDetailsService.deleteProfileRelationshipStatus(profileId);
        });
    }

    @Test
    final void test_deleteProfileRelationshipStatus_success() {
        // given
        String profileId = "profileId";
        Profile profile = Profile
                .builder()
                .relationshipStatus(new RelationshipStatus())
                .build();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutDetailsService.deleteProfileRelationshipStatus(profileId);

        // then
        Assertions.assertEquals(expected, actual);
        verify(relationshipStatusRepository).delete(profile.getRelationshipStatus());
    }

    @Test
    final void test_deleteProfileHomeTown_profileNotFound() {
        // given
        String profileId = "profileId";

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDetailsService.deleteProfileHomeTown(profileId);
        });
    }

    @Test
    final void test_deleteProfileHomeTown_homeTownIsNull() {
        // given
        String profileId = "profileId";
        Profile profile = Profile
                .builder()
                .build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));

        // then
        Assertions.assertThrows(ProfileDetailsNotFoundException.class, () -> {
            aboutDetailsService.deleteProfileHomeTown(profileId);
        });
    }

    @Test
    final void test_deleteProfileHomeTown_success() {
        // given
        String profileId = "profileId";
        Profile profile = Profile
                .builder()
                .homeTown(new AboutDetails())
                .build();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutDetailsService.deleteProfileHomeTown(profileId);

        // then
        Assertions.assertEquals(expected, actual);
        verify(aboutDetailsRepository).delete(profile.getHomeTown());
    }

    @Test
    final void test_deleteProfileCurrentCity_profileNotFound() {
        // given
        String profileId = "profileId";

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDetailsService.deleteProfileCurrentCity(profileId);
        });
    }

    @Test
    final void test_deleteProfileCurrentCity_currentCityIsNull() {
        // given
        String profileId = "profileId";
        Profile profile = Profile
                .builder()
                .build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));

        // then
        Assertions.assertThrows(ProfileDetailsNotFoundException.class, () -> {
            aboutDetailsService.deleteProfileCurrentCity(profileId);
        });
    }

    @Test
    final void test_deleteProfileCurrentCity_success() {
        // given
        String profileId = "profileId";
        Profile profile = Profile
                .builder()
                .currentCity(new AboutDetails())
                .build();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutDetailsService.deleteProfileCurrentCity(profileId);

        // then
        Assertions.assertEquals(expected, actual);
        verify(aboutDetailsRepository).delete(profile.getCurrentCity());
    }
}
