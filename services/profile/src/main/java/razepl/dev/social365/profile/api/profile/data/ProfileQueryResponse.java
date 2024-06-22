package razepl.dev.social365.profile.api.profile.data;

import lombok.Builder;

@Builder
public record ProfileQueryResponse(String fullName, String profileId, String profilePictureUrl) {
}
