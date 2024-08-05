package razepl.dev.social365.auth.clients.profile.data;

import lombok.Builder;

@Builder
public record Profile(String profileId, String username, String fullName, String bio,
                      String profilePictureUrl, String subtitle, String profileBannerUrl) {
}
