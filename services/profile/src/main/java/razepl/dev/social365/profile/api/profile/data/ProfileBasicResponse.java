package razepl.dev.social365.profile.api.profile.data;

import lombok.Builder;

@Builder
public record ProfileBasicResponse(String profileId, String username, String fullName, String bio,
                                   String profilePictureUrl, String subtitle, String profileBannerUrl,
                                   boolean isFollowing, boolean isFriend) {
}
