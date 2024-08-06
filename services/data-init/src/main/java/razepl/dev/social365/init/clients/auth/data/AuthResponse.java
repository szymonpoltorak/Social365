package razepl.dev.social365.init.clients.auth.data;

import lombok.Builder;
import razepl.dev.social365.init.clients.profile.data.Profile;

@Builder
public record AuthResponse(TokenResponse token, Profile profile) {
}
