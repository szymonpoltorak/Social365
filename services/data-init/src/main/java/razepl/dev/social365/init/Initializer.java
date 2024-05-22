package razepl.dev.social365.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import razepl.dev.social365.init.clients.posts.comments.PostCommentsService;
import razepl.dev.social365.init.clients.posts.comments.constants.CommentRequest;
import razepl.dev.social365.init.clients.posts.comments.data.PostResponse;
import razepl.dev.social365.init.clients.profile.ProfileService;
import razepl.dev.social365.init.clients.profile.data.ProfileRequest;
import razepl.dev.social365.init.clients.profile.data.ProfileResponse;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.random.RandomGenerator;

@Configuration
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {

    private final ProfileService profileService;
    private final PostCommentsService postCommentsService;

    @Override
    public final void run(String... args) {
        final int numberOfProfiles = 10;
        List<String> firstNames = List.of("Andrzej", "Jan", "Tomasz", "Piotr", "Pawel", "Mariusz",
                "Wojciech", "Kacper", "Kamil", "Krzysztof");
        List<String> lastNames = List.of("Kowalski", "Nowak", "Wojcik", "Kaminski", "Lewandowski");
        Collection<ProfileRequest> profileRequests = new ArrayList<>(numberOfProfiles);
        List<ProfileResponse> profiles = new ArrayList<>(numberOfProfiles);
        RandomGenerator random = new SecureRandom();

        for (int i = 0; i < numberOfProfiles; i++) {
            String firstName = firstNames.get(i);
            String lastName = lastNames.get(random.nextInt(lastNames.size()));
            String email = firstName.toLowerCase(Locale.ROOT) + "." + lastName.toLowerCase(Locale.ROOT) + "@example.com";
            LocalDate dateOfBirth = LocalDate.now().minusYears(13 + random.nextInt(50));

            ProfileRequest profileRequest = ProfileRequest.builder()
                    .userId((long) i)
                    .username(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .dateOfBirth(dateOfBirth)
                    .build();
            profileRequests.add(profileRequest);
        }

        profileRequests.forEach(request -> {
            ProfileResponse profile = profileService.createUsersProfile(request);

            profiles.add(profile);
        });

        for (int i = 0; i < profiles.size(); i++) {
            for (int j = 0; j < profiles.size(); j++) {
                try {
                    if (i != j) {
                        profileService.addUserToFriends(profiles.get(i).profileId(), profiles.get(j).profileId());
                    }
                } catch (Exception e) {
                    // ignore
                }
            }
        }

        List<String> postContents = List.of("This is my first post!", "Enjoying a beautiful sunset.", "Had a great time at the park today.", "Love spending time with family.", "Just finished a good book.");
        List<String> commentContents = List.of("Great post!", "Thanks for sharing.", "Looks fun!", "I agree.", "Nice picture.");

        profiles.forEach(profile -> {
            String postContent = postContents.get(random.nextInt(postContents.size()));
            PostResponse postResponse = postCommentsService.createPost(profile.profileId(), postContent, false);

            for (int i = 0; i < commentContents.size(); i++) {
                String commentContent = commentContents.get(random.nextInt(commentContents.size()));
                CommentRequest commentRequest = CommentRequest
                        .builder()
                        .profileId(profile.profileId())
                        .objectId(postResponse.postId())
                        .content(commentContent)
                        .build();
                postCommentsService.addCommentToPost(commentRequest);
            }
        });

        System.exit(0);
    }
}
