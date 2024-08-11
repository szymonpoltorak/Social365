package razepl.dev.social365.auth.api.auth.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import razepl.dev.social365.auth.api.auth.data.AuthResponse;
import razepl.dev.social365.auth.api.auth.data.LoginRequest;
import razepl.dev.social365.auth.api.auth.data.RegisterRequest;
import razepl.dev.social365.auth.api.auth.data.ResetPasswordRequest;
import razepl.dev.social365.auth.api.auth.data.SimpleStringResponse;
import razepl.dev.social365.auth.api.auth.data.TokenResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest userRequest, HttpServletRequest request);

    AuthResponse login(LoginRequest loginRequest, HttpServletRequest request);

    TokenResponse refreshToken(String refreshToken);

    SimpleStringResponse requestResetUsersPassword(String username);

    SimpleStringResponse resetUsersPassword(ResetPasswordRequest request);

}

