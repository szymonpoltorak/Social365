package razepl.dev.social365.profile.config.auth;

import lombok.Builder;

@Builder
public record User(long userId, String username, String profileId) {
}
