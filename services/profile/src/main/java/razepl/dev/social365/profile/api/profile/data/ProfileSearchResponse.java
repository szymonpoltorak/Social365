package razepl.dev.social365.profile.api.profile.data;

import lombok.Builder;

@Builder
public record ProfileSearchResponse(String fullName, String profileId, String profilePictureUrl,
                                    String subtitle, String username) {
}
