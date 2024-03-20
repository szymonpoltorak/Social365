package razepl.dev.social365.profile.api.profile.data;

import lombok.Builder;

@Builder
public record ProfileResponse(String username, String fullName, String bio, String profilePictureLink) {
}
