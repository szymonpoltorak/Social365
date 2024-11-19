package razepl.dev.social365.profile.api.friends.data;

import lombok.Builder;

@Builder
public record FriendProfile(String profileId, long userId, String firstName, String lastName, String bio,
                            long profilePictureId, long bannerPictureId, boolean isOnline, String email) {

    public String getFullName() {
        return "%s %s".formatted(firstName, lastName);
    }

}
