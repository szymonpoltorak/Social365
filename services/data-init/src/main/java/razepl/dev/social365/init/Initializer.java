package razepl.dev.social365.init;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import razepl.dev.social365.init.clients.api.GatewayService;
import razepl.dev.social365.init.clients.api.data.AuthResponse;
import razepl.dev.social365.init.clients.api.data.RegisterRequest;
import razepl.dev.social365.init.clients.api.constants.CommentAddRequest;
import razepl.dev.social365.init.clients.api.data.PostResponse;
import razepl.dev.social365.init.clients.api.data.Profile;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {

    private static final int MINIMAL_AGE = 13;
    private static final int MAX_BOUND = 50;
    private static final int NUM_OF_PROFILES = 10;
    private static final int BEGIN_RANGE = 0;
    private static final String PASSWORD = "Abc1!l1.DKk";

    private final GatewayService gatewayService;
    private final JwtDecoder jwtDecoder;

    @Override
    public final void run(String... args) {
        List<String> firstNames = List.of("Andrzej", "Jan", "Tomasz", "Piotr", "Pawel", "Mariusz",
                "Wojciech", "Kacper", "Kamil", "Krzysztof");
        List<String> lastNames = List.of("Kowalski", "Nowak", "Wojcik", "Kaminski", "Lewandowski");
        RandomGenerator random = new SecureRandom();

        Collection<RegisterRequest> registerRequests = IntStream
                .range(BEGIN_RANGE, NUM_OF_PROFILES)
                .mapToObj(i -> {
                    String firstName = firstNames.get(i);
                    String lastName = lastNames.get(random.nextInt(lastNames.size()));
                    String email = "%s.%s@gmail.com".formatted(firstName.toLowerCase(Locale.US), lastName.toLowerCase(Locale.US));
                    LocalDate dateOfBirth = LocalDate.now().minusYears((MINIMAL_AGE + random.nextInt(MAX_BOUND)));

                    return RegisterRequest
                            .builder()
                            .username(email)
                            .password(PASSWORD)
                            .firstName(firstName)
                            .lastName(lastName)
                            .dateOfBirth(dateOfBirth)
                            .build();
                })
                .toList();

        List<AuthResponse> registeredUsers = registerRequests
                .stream()
                .map(request -> {
                    try {
                        return gatewayService.registerUser(request);
                    } catch (FeignException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .filter(user -> user.profile().profileId() != null && !user.profile().profileId().isEmpty())
                .toList();

        printList(registeredUsers.stream().map(AuthResponse::profile).toList());

        registeredUsers.forEach(user -> registeredUsers.forEach(friendUser -> {
            Profile profile = user.profile();
            Profile friend = friendUser.profile();

            setAuthentication(user);

            if (profile.profileId().equals(friend.profileId())) {
                return;
            }
            try {
                gatewayService.addUserToFriends(profile.profileId(), friend.profileId());
            } catch (FeignException e) {
                // user are already friends ignore
            }
        }));

        createPostsForProfiles(registeredUsers, random);

        System.exit(0);
    }

    private void setAuthentication(AuthResponse user) {
        SecurityContextHolder.getContext().setAuthentication(
                new JwtAuthenticationToken(jwtDecoder.decode(user.token().authToken()))
        );
    }

    private void createPostsForProfiles(Iterable<AuthResponse> registeredUsers, RandomGenerator random) {
        List<String> postContents = List.of("This is my first post!", "Enjoying a beautiful sunset.",
                "Had a great time at the park today.", "Love spending time with family.", "Just finished a good book.");
        List<String> commentContents = List.of("Great post!", "Thanks for sharing.", "Looks fun!",
                "I agree.", "Nice picture.");

        int commentsContentCount = commentContents.size();
        int postsContentCount = postContents.size();

        registeredUsers
                .forEach(user -> {
                    Profile profile = user.profile();
                    String postContent = postContents.get(random.nextInt(postsContentCount));

                    setAuthentication(user);

                    PostResponse postResponse = gatewayService.createPost(profile.profileId(), postContent, false);

                    log.info("Adding post: {}", postResponse);

                    for (int i = 0; i < commentsContentCount; i++) {
                        String commentContent = commentContents.get(random.nextInt(commentsContentCount));
                        CommentAddRequest commentAddRequest = CommentAddRequest
                                .builder()
                                .profileId(profile.profileId())
                                .postId(postResponse.postKey().postId())
                                .content(commentContent)
                                .hasAttachment(false)
                                .build();
                        log.info("Adding comment: {}", commentAddRequest);

                        gatewayService.addCommentToPost(commentAddRequest);
                    }
                });
    }

    private <T> void printList(List<T> list) {
        try {
            log.info("Profile: \n{}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(list));

        } catch (JsonProcessingException e) {
            log.error("Error while printing list: {}", e.getMessage());
        }
    }
}
