package razepl.dev.social365.auth.config.api.auth.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import razepl.dev.social365.auth.config.api.auth.data.AuthResponse;
import razepl.dev.social365.auth.config.api.auth.data.LoginRequest;
import razepl.dev.social365.auth.config.api.auth.data.RegisterRequest;
import razepl.dev.social365.auth.config.api.auth.data.ResetPasswordRequest;
import razepl.dev.social365.auth.config.api.auth.data.SimpleStringResponse;

public interface AuthController {

    AuthResponse registerUser(RegisterRequest registerRequest, HttpServletRequest request);

    AuthResponse loginUser(LoginRequest loginRequest, HttpServletRequest request);

    AuthResponse refreshUserToken(String refreshToken);

    SimpleStringResponse requestResetUsersPassword(String username);

    SimpleStringResponse resetUsersPassword(ResetPasswordRequest request);

}
