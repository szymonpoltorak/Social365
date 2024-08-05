package razepl.dev.social365.auth.api.auth.data;

import lombok.Builder;
import razepl.dev.social365.auth.clients.profile.data.Profile;

@Builder
public record AuthResponse(TokenResponse token, Profile profile) {
}
