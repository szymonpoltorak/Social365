package razepl.dev.social365.init.clients.auth.data;

import lombok.Builder;

@Builder
public record TokenResponse(String authToken, String refreshToken) {
}
