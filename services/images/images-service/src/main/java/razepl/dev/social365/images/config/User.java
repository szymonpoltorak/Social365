package razepl.dev.social365.images.config;

import lombok.Builder;

@Builder
public record User(long userId, String username, String profileId) {
}
