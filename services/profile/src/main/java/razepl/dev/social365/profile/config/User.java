package razepl.dev.social365.profile.config;

import lombok.Builder;

@Builder
public record User(long userId, String username, String profileId) {
}
