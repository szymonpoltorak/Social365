package razepl.dev.social365.notifications.config.auth;

import lombok.Builder;

@Builder
public record User(long userId, String username, String profileId) {
}
