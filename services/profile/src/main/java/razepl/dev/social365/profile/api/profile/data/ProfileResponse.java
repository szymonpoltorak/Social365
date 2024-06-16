package razepl.dev.social365.profile.api.profile.data;

import lombok.Builder;

@Builder
public record ProfileResponse(String profileId, String username, String fullName, String bio,
                              String profilePictureUrl) {
}
