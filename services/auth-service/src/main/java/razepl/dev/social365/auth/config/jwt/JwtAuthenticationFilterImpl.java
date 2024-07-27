package razepl.dev.social365.auth.config.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import razepl.dev.social365.auth.config.jwt.interfaces.JwtAuthenticationFilter;
import razepl.dev.social365.auth.config.jwt.interfaces.JwtService;
import razepl.dev.social365.auth.entities.user.interfaces.ServiceUser;
import razepl.dev.social365.auth.entities.user.interfaces.UserRepository;
import razepl.dev.social365.auth.exceptions.auth.throwable.UserDoesNotExistException;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilterImpl extends OncePerRequestFilter implements JwtAuthenticationFilter {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public final void doFilterInternal(@NonNull HttpServletRequest request,
                                       @NonNull HttpServletResponse response,
                                       @NonNull FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = jwtService.getJwtTokenFromRequest(request);

        token.ifPresent(authToken -> {
            Optional<String> usernameOptional = jwtService.getClaimFromToken(authToken, Claims::getSubject);

            usernameOptional.ifPresent(username -> setTokenForNotAuthenticatedUser(authToken, username, request));
        });

        filterChain.doFilter(request, response);
    }

    private void setTokenForNotAuthenticatedUser(String jwtToken, String username, HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }
        ServiceUser userDetails = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserDoesNotExistException(username));

        if (jwtService.isTokenValid(jwtToken, userDetails)) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

}
