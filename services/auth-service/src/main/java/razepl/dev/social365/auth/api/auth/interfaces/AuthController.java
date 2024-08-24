package razepl.dev.social365.auth.api.auth.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import razepl.dev.social365.auth.api.auth.data.AuthResponse;
import razepl.dev.social365.auth.api.auth.data.LoginRequest;
import razepl.dev.social365.auth.api.auth.data.RegisterRequest;
import razepl.dev.social365.auth.api.auth.data.ResetPasswordRequest;
import razepl.dev.social365.auth.api.auth.data.SimpleStringResponse;
import razepl.dev.social365.auth.api.auth.data.TokenResponse;

@Tag(name = "Authorisation Controller", description = "The Authorisation API")
public interface AuthController {

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    AuthResponse registerUser(RegisterRequest registerRequest, HttpServletRequest request);

    @Operation(summary = "Login a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    AuthResponse loginUser(LoginRequest loginRequest, HttpServletRequest request);

    @Operation(summary = "Refresh user token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid token",
                    content = @Content)
    })
    TokenResponse refreshUserToken(String refreshToken);

    @Operation(summary = "Request password reset for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset requested successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SimpleStringResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    SimpleStringResponse requestResetUsersPassword(String username);

    @Operation(summary = "Reset user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SimpleStringResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    SimpleStringResponse resetUsersPassword(ResetPasswordRequest request);

}