package razepl.dev.social365.init.clients.api.data;

import lombok.Builder;

@Builder
public record TokenResponse(String authToken, String refreshToken) {
}
