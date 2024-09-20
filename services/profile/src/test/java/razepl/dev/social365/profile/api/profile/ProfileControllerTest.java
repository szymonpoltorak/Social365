package razepl.dev.social365.profile.api.profile;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startable;
import org.testcontainers.utility.DockerImageName;
import razepl.dev.social365.profile.api.profile.constants.ProfileMappings;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;
import razepl.dev.social365.profile.api.profile.interfaces.ProfileService;
import razepl.dev.social365.profile.clients.images.ImagesServiceClient;
import razepl.dev.social365.profile.clients.images.data.ImageResponse;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDateRepository;
import razepl.dev.social365.profile.nodes.about.mail.interfaces.EmailRepository;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.mockito.Mockito.when;
import static razepl.dev.social365.profile.TestConfig.NEO4J_IMAGE;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileControllerTest {

    @Container
    @ServiceConnection
    private static final Startable neo4jContainer = new Neo4jContainer<>(DockerImageName.parse(NEO4J_IMAGE));

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BirthDateRepository birthDateRepository;

    @Autowired
    private EmailRepository mailRepository;

    @MockBean
    private ImagesServiceClient imagesServiceClient;

    @BeforeAll
    static void beforeAll() {
        neo4jContainer.start();
    }

    @AfterAll
    static void afterAll() {
        neo4jContainer.stop();
    }

    @BeforeEach
    final void beforeEach() {
        profileRepository.deleteAll();
        birthDateRepository.deleteAll();
        mailRepository.deleteAll();
    }

    @Test
    final void test_getProfileSummary_success() {
        // given
        ProfileRequest profileRequest = ProfileRequest
                .builder()
                .userId(1L)
                .firstName("No")
                .lastName("User")
                .username("nouser@example.com")
                .dateOfBirth(LocalDate.now().minusYears(20).format(DateTimeFormatter.ISO_LOCAL_DATE))
                .build();

        when(imagesServiceClient.getImagePath(1L))
                .thenReturn(ImageResponse.builder().imagePath("my/path").build());

        ProfileResponse profileResponse = profileService.createUsersProfile(profileRequest);
        Map<String, String> uriVariables = Map.of("profileId", profileResponse.profileId());

        // when
        ProfileSummaryResponse actual = restTemplate
                .getForObject(ProfileMappings.GET_PROFILE_SUMMARY_MAPPING, ProfileSummaryResponse.class, uriVariables);

        // then
        Assertions.assertNotNull(actual);
        profileRepository.deleteById(profileResponse.profileId());
    }

    @Test
    final void test_getPostProfileInfo_success() {
        // given
        ProfileRequest profileRequest = ProfileRequest
                .builder()
                .userId(1L)
                .firstName("No")
                .lastName("User")
                .username("nouser@example.com")
                .dateOfBirth(LocalDate.now().minusYears(20).format(DateTimeFormatter.ISO_LOCAL_DATE))
                .build();

        when(imagesServiceClient.getImagePath(1L))
                .thenReturn(ImageResponse.builder().imagePath("my/path").build());

        ProfileResponse profileResponse = profileService.createUsersProfile(profileRequest);
        Map<String, String> uriVariables = Map.of("profileId", profileResponse.profileId());

        // when
        ProfilePostResponse actual = restTemplate
                .getForObject(ProfileMappings.GET_POST_PROFILE_INFO_MAPPING, ProfilePostResponse.class, uriVariables);

        // then
        Assertions.assertNotNull(actual);
        profileRepository.deleteById(profileResponse.profileId());
    }

    @Test
    final void test_updateProfileBio_success() {
        // given
        ProfileRequest profileRequest = ProfileRequest
                .builder()
                .userId(1L)
                .firstName("No")
                .lastName("User")
                .username("nouser@example.com")
                .dateOfBirth(LocalDate.now().minusYears(20).format(DateTimeFormatter.ISO_LOCAL_DATE))
                .build();

        when(imagesServiceClient.getImagePath(1L))
                .thenReturn(ImageResponse.builder().imagePath("my/path").build());

        ProfileResponse profileResponse = profileService.createUsersProfile(profileRequest);
        Map<String, String> uriVariables = Map.of(
                "profileId", profileResponse.profileId(),
                "bio", "I am a user"
        );

        // when
        ProfileRequest actual = restTemplate
                .postForObject(ProfileMappings.UPDATE_PROFILE_BIO_MAPPING, uriVariables, ProfileRequest.class);

        // then
        Assertions.assertNotNull(actual);

        profileRepository.deleteById(profileResponse.profileId());
    }

    @Test
    final void test_getBasicProfileInfo_success() {
        // given
        ProfileRequest profileRequest = ProfileRequest
                .builder()
                .userId(1L)
                .firstName("No")
                .lastName("User")
                .username("nouser@example.com")
                .dateOfBirth(LocalDate.now().minusYears(20).format(DateTimeFormatter.ISO_LOCAL_DATE))
                .build();

        when(imagesServiceClient.getImagePath(1L))
                .thenReturn(ImageResponse.builder().imagePath("my/path").build());

        ProfileResponse profileResponse = profileService.createUsersProfile(profileRequest);
        Map<String, String> uriVariables = Map.of("profileId", profileResponse.profileId());

        // when
        ProfileResponse actual = restTemplate
                .getForObject(ProfileMappings.GET_BASIC_PROFILE_INFO_MAPPING, ProfileResponse.class, uriVariables);

        // then
        Assertions.assertNotNull(actual);

        profileRepository.deleteById(profileResponse.profileId());
    }
}
