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
import razepl.dev.social365.profile.config.auth.User;
import razepl.dev.social365.profile.utils.exceptions.IllegalDetailsTypeException;
import razepl.dev.social365.profile.utils.exceptions.ProfileDetailsNotFoundException;
import razepl.dev.social365.profile.utils.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.utils.exceptions.TooYoungForAccountException;
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
import java.time.format.DateTimeFormatter;
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
        String profileId = "profileId";
        GenderRequest genderRequest = GenderRequest
                .builder()
                .gender(GenderType.MALE)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        User user = User.builder().profileId(profileId).build();
        Gender gender = Gender
                .builder()
                .genderType(genderRequest.gender())
                .privacyLevel(genderRequest.privacyLevel())
                .build();
        Profile profile = Profile
                .builder()
                .profileId(profileId)
                .build();
        ProfileRequest expected = ProfileRequest
                .builder()
                .build();

        // when
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.of(profile));
        when(genderRepository.save(gender))
                .thenReturn(gender);
        when(profileRepository.save(profile))
                .thenReturn(profile);
        when(genderRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.of(gender));
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutDetailsService.updateProfileGender(user, genderRequest);

        // then
        Assertions.assertEquals(expected, actual);
        verify(genderRepository).save(gender);
    }

    @Test
    final void test_updateProfileGender_profileNotFound() {
        // given
        String profileId = "profileId";
        User user = User.builder().profileId(profileId).build();
        GenderRequest genderRequest = GenderRequest
                .builder()
                .gender(GenderType.MALE)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();

        // when
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDetailsService.updateProfileGender(user, genderRequest);
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
        String profileId = "profileId";
        User user = User.builder().profileId(profileId).build();
        DateOfBirthRequest dateOfBirthRequest = DateOfBirthRequest
                .builder()
                .dateOfBirth(null)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();

        // when
        when(dateOfBirthRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDetailsService.updateProfileDateOfBirth(user, dateOfBirthRequest);
        });
    }

    @Test
    final void test_updateProfileDateOfBirth_tooYoung() {
        // given
        String profileId = "profileId";
        User user = User.builder().profileId(profileId).build();
        DateOfBirthRequest dateOfBirthRequest = DateOfBirthRequest
                .builder()
                .dateOfBirth(LocalDate.now())
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        BirthDate birthDate = BirthDate
                .builder()
                .dateOfBirth(dateOfBirthRequest.dateOfBirth().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .privacyLevel(dateOfBirthRequest.privacyLevel())
                .build();

        // when
        when(dateOfBirthRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.of(birthDate));
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.of(Profile.builder().build()));

        // then
        Assertions.assertThrows(TooYoungForAccountException.class, () -> {
            aboutDetailsService.updateProfileDateOfBirth(user, dateOfBirthRequest);
        });
    }

    @Test
    final void test_updateProfileDateOfBirth_illegalDate() {
        // given
        String profileId = "profileId";
        User user = User.builder().profileId(profileId).build();
        DateOfBirthRequest dateOfBirthRequest = DateOfBirthRequest
                .builder()
                .dateOfBirth(LocalDate.now().plusYears(1))
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        BirthDate birthDate = BirthDate
                .builder()
                .dateOfBirth(dateOfBirthRequest.dateOfBirth().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .privacyLevel(dateOfBirthRequest.privacyLevel())
                .build();

        // when
        when(dateOfBirthRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.of(birthDate));
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.of(Profile.builder().build()));

        // then
        Assertions.assertThrows(TooYoungForAccountException.class, () -> {
            aboutDetailsService.updateProfileDateOfBirth(user, dateOfBirthRequest);
        });
    }

    @Test
    final void test_updateProfileDateOfBirth_success() {
        // given
        String profileId = "profileId";
        User user = User.builder().profileId(profileId).build();
        DateOfBirthRequest dateOfBirthRequest = DateOfBirthRequest
                .builder()
                .dateOfBirth(LocalDate.now().minusYears(20))
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        BirthDate birthDate = BirthDate
                .builder()
                .dateOfBirth(dateOfBirthRequest.dateOfBirth().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .privacyLevel(dateOfBirthRequest.privacyLevel())
                .build();
        Profile profile = Profile
                .builder()
                .profileId(profileId)
                .birthDate(birthDate)
                .build();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.of(profile));
        when(dateOfBirthRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.of(birthDate));
        when(dateOfBirthRepository.save(birthDate))
                .thenReturn(birthDate);
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutDetailsService.updateProfileDateOfBirth(user, dateOfBirthRequest);

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    final void test_updateProfileRelationshipStatus_profileNotFound() {
        // given
        String profileId = "profileId";
        User user = User.builder().profileId(profileId).build();
        RelationshipStatusRequest relationshipStatusRequest = RelationshipStatusRequest
                .builder()
                .relationshipStatus(RelationshipStatusType.SINGLE)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();

        // when
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDetailsService.updateProfileRelationshipStatus(user, relationshipStatusRequest);
        });
    }

    @Test
    final void test_updateProfileRelationshipStatus_success() {
        // given
        String profileId = "profileId";
        User user = User.builder().profileId(profileId).build();
        RelationshipStatusRequest relationshipStatusRequest = RelationshipStatusRequest
                .builder()
                .relationshipStatus(RelationshipStatusType.SINGLE)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        Profile profile = Profile
                .builder()
                .profileId(user.profileId())
                .relationshipStatus(new RelationshipStatus())
                .build();
        RelationshipStatus status = profile.getRelationshipStatus();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.of(profile));
        when(relationshipStatusRepository.findByProfileId(profile.getProfileId()))
                .thenReturn(Optional.of(profile.getRelationshipStatus()));
        when(relationshipStatusRepository.save(status))
                .thenReturn(status);
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);
        when(profileRepository.save(profile))
                .thenReturn(profile);

        ProfileRequest actual = aboutDetailsService.updateProfileRelationshipStatus(user, relationshipStatusRequest);

        // then
        Assertions.assertEquals(expected, actual);
        verify(relationshipStatusRepository).save(status);
    }

    @Test
    final void test_updateProfileCurrentCity_profileNotFound() {
        // given
        String profileId = "profileId";
        User user = User.builder().profileId(profileId).build();
        AboutDetailsRequest cityRequest = AboutDetailsRequest
                .builder()
                .detailsValue("city")
                .detailsType(DetailsType.CURRENT_CITY)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();

        // when
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDetailsService.updateProfileCurrentCity(user, cityRequest);
        });
    }

    @Test
    final void test_updateProfileCurrentCity_wrongDetailsType() {
        // given
        String profileId = "profileId";
        User user = User.builder().profileId(profileId).build();
        AboutDetailsRequest cityRequest = AboutDetailsRequest
                .builder()
                .detailsValue("city")
                .detailsType(DetailsType.HOMETOWN)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        Profile profile = Profile
                .builder()
                .build();

        // when
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.of(profile));

        // then
        Assertions.assertThrows(IllegalDetailsTypeException.class, () -> {
            aboutDetailsService.updateProfileCurrentCity(user, cityRequest);
        });
    }

    @Test
    final void test_updateProfileCurrentCity_success() {
        // given
        String profileId = "profileId";
        User user = User.builder().profileId(profileId).build();
        AboutDetailsRequest cityRequest = AboutDetailsRequest
                .builder()
                .detailsValue("city")
                .detailsType(DetailsType.CURRENT_CITY)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        Profile profile = Profile
                .builder()
                .profileId("profileId")
                .currentCity(new AboutDetails())
                .build();
        AboutDetails currentCity = profile.getCurrentCity();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.of(profile));
        when(aboutDetailsRepository.findCurrentCityByProfileId(profile.getProfileId()))
                .thenReturn(Optional.of(profile.getCurrentCity()));
        when(aboutDetailsRepository.save(currentCity))
                .thenReturn(currentCity);
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);
        when(profileRepository.save(profile))
                .thenReturn(profile);

        ProfileRequest actual = aboutDetailsService.updateProfileCurrentCity(user, cityRequest);

        // then
        Assertions.assertEquals(expected, actual);
        verify(aboutDetailsRepository).save(currentCity);
    }

    @Test
    final void test_updateProfileHomeTown_profileNotFound() {
        // given
        String profileId = "profileId";
        User user = User.builder().profileId(profileId).build();
        AboutDetailsRequest homeRequest = AboutDetailsRequest
                .builder()
                .detailsValue("city")
                .detailsType(DetailsType.HOMETOWN)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();

        // when
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProfileNotFoundException.class, () -> {
            aboutDetailsService.updateProfileHomeTown(user, homeRequest);
        });
    }

    @Test
    final void test_updateProfileHomeTown_wrongDetailsType() {
        // given
        String profileId = "profileId";
        User user = User.builder().profileId(profileId).build();
        AboutDetailsRequest homeRequest = AboutDetailsRequest
                .builder()
                .detailsValue("city")
                .detailsType(DetailsType.CURRENT_CITY)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        Profile profile = Profile
                .builder()
                .build();

        // when
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.of(profile));

        // then
        Assertions.assertThrows(IllegalDetailsTypeException.class, () -> {
            aboutDetailsService.updateProfileHomeTown(user, homeRequest);
        });
    }

    @Test
    final void test_updateProfileHomeTown_success() {
        // given
        String profileId = "profileId";
        User user = User.builder().profileId(profileId).build();
        AboutDetailsRequest homeRequest = AboutDetailsRequest
                .builder()
                .detailsValue("city")
                .detailsType(DetailsType.HOMETOWN)
                .privacyLevel(PrivacyLevel.FRIENDS)
                .build();
        Profile profile = Profile
                .builder()
                .profileId(user.profileId())
                .homeTown(new AboutDetails())
                .build();
        AboutDetails homeTown = profile.getHomeTown();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(user.profileId()))
                .thenReturn(Optional.of(profile));
        when(aboutDetailsRepository.findHomeTownByProfileId(profile.getProfileId()))
                .thenReturn(Optional.of(profile.getHomeTown()));
        when(aboutDetailsRepository.save(homeTown))
                .thenReturn(homeTown);
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);
        when(profileRepository.save(profile))
                .thenReturn(profile);

        ProfileRequest actual = aboutDetailsService.updateProfileHomeTown(user, homeRequest);

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
                .profileId(profileId)
                .relationshipStatus(new RelationshipStatus())
                .build();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(relationshipStatusRepository.findByProfileId(profile.getProfileId()))
                .thenReturn(Optional.of(profile.getRelationshipStatus()));
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
                .profileId(profileId)
                .homeTown(new AboutDetails())
                .build();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(aboutDetailsRepository.findHomeTownByProfileId(profileId))
                .thenReturn(Optional.of(profile.getHomeTown()));
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
                .profileId(profileId)
                .currentCity(new AboutDetails())
                .build();
        ProfileRequest expected = ProfileRequest.builder().build();

        // when
        when(profileRepository.findByProfileId(profileId))
                .thenReturn(Optional.of(profile));
        when(aboutDetailsRepository.findCurrentCityByProfileId(profileId))
                .thenReturn(Optional.of(profile.getCurrentCity()));
        when(profileMapper.mapProfileToProfileRequest(profile))
                .thenReturn(expected);

        ProfileRequest actual = aboutDetailsService.deleteProfileCurrentCity(profileId);

        // then
        Assertions.assertEquals(expected, actual);
        verify(aboutDetailsRepository).delete(profile.getCurrentCity());
    }
}
