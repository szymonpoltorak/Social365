package razepl.dev.social365.posts.config.auth;

import lombok.Builder;

@Builder
public record User(long userId, String username, String profileId) {
}
