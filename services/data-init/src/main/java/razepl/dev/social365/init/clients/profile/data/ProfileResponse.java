package razepl.dev.social365.init.clients.profile.data;

import lombok.Builder;

@Builder
public record ProfileResponse(String profileId, String username, String fullName, String bio, String profilePictureLink) {
}
