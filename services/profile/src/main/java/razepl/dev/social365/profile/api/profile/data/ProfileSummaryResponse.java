package razepl.dev.social365.profile.api.profile.data;

import lombok.Builder;

@Builder
public record ProfileSummaryResponse(String fullName, String username, String subtitle,
                                     String bio, String profilePictureUrl, String profileBannerUrl,
                                     int followersCount, int friendsCount, int postsCount, String profileId) {
}
