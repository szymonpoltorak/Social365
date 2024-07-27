package razepl.dev.social365.auth.config.api.auth.data;

import lombok.Builder;

@Builder
public record LoginRequest(String username, String password) {
}
