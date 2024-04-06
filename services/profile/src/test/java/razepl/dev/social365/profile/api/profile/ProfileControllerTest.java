package razepl.dev.social365.profile.api.profile;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startable;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;
import razepl.dev.social365.profile.api.profile.constants.ProfileMappings;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;
import razepl.dev.social365.profile.api.profile.interfaces.ProfileService;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

import java.time.LocalDate;
import java.util.Calendar;

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
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @BeforeAll
    static void beforeAll() {
        neo4jContainer.start();
    }

    @AfterAll
    static void afterAll() {
        neo4jContainer.stop();
    }

    @Test
    final void test_getProfileSummary() {
        // given
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -20);
        ProfileRequest profileRequest = ProfileRequest
                .builder()
                .name("No")
                .lastName("User")
                .username("nouser@example.com")
                .dateOfBirth(calendar.getTime())
                .build();

        System.out.println(new ObjectMapper());

        profileService.createUsersProfile(profileRequest);

        // when
        ProfileSummaryResponse actual = restTemplate
                .getForObject(ProfileMappings.GET_PROFILE_SUMMARY_MAPPING, ProfileSummaryResponse.class);

        // then
        Assertions.assertNotNull(actual);

    }

}
