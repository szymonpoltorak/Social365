package razepl.dev.social365.notifications.config;

import lombok.Builder;

@Builder
public record User(long userId, String username, String profileId) {
}
