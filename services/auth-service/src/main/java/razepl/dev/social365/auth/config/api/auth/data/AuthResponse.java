package razepl.dev.social365.auth.config.api.auth.data;

import lombok.Builder;

@Builder
public record AuthResponse(String authToken, String refreshToken) {
}
