package razepl.dev.social365.posts.clients.profile.data;

import lombok.Builder;

@Builder
public record Profile(String profileId, String username, String fullName,
                      String subtitle, String profilePictureUrl) {
}
