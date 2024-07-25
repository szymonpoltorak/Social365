package razepl.dev.social365.init.clients.profile.data;

import lombok.Builder;

@Builder
public record Profile(String profileId, String username, String fullName,
                      String subtitle, String profilePictureUrl) {
}
