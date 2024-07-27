package razepl.dev.social365.auth.config.api.auth.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import razepl.dev.social365.auth.config.api.auth.data.AuthResponse;
import razepl.dev.social365.auth.config.api.auth.data.LoginRequest;
import razepl.dev.social365.auth.config.api.auth.data.RegisterRequest;
import razepl.dev.social365.auth.config.api.auth.data.ResetPasswordRequest;
import razepl.dev.social365.auth.config.api.auth.data.SimpleStringResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest userRequest, HttpServletRequest request);

    AuthResponse login(LoginRequest loginRequest, HttpServletRequest request);

    AuthResponse refreshToken(String refreshToken);

    SimpleStringResponse requestResetUsersPassword(String username);

    SimpleStringResponse resetUsersPassword(ResetPasswordRequest request);

}

