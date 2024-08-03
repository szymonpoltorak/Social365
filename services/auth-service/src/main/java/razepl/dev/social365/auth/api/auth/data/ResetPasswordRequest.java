package razepl.dev.social365.auth.api.auth.data;

import lombok.Builder;
import razepl.dev.social365.auth.entities.user.interfaces.Password;

@Builder
public record ResetPasswordRequest(@Password String newPassword, String resetPasswordToken) {
}
