package razepl.dev.social365.auth.api.auth.interfaces;


import razepl.dev.social365.auth.api.auth.data.AuthResponse;
import razepl.dev.social365.auth.api.auth.data.LoginRequest;
import razepl.dev.social365.auth.api.auth.data.RegisterRequest;
import razepl.dev.social365.auth.api.auth.data.ResetPasswordRequest;
import razepl.dev.social365.auth.api.auth.data.TokenResponse;
import razepl.dev.social365.auth.clients.profile.data.Profile;
import razepl.dev.social365.auth.config.constants.TokenRevokeStatus;
import razepl.dev.social365.auth.entities.attempts.LoginAttempt;
import razepl.dev.social365.auth.entities.user.User;

public interface AuthHelperService {

    User buildRequestIntoUser(RegisterRequest registerRequest, LoginAttempt loginAttempt);

    void executeUserAuthenticationProcess(LoginAttempt loginAttempt, LoginRequest loginRequest);

    void executePasswordResetProcess(ResetPasswordRequest request, User user);

    TokenResponse buildTokensIntoResponse(String authToken, String refreshToken);

    AuthResponse buildTokensIntoResponse(User user, Profile profile, TokenRevokeStatus shouldBeRevoked);

}
