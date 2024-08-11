package razepl.dev.social365.auth.api.auth.data;

import lombok.Builder;

@Builder
public record TokenResponse(String authToken, String refreshToken) {
}
