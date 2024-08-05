package razepl.dev.social365.auth.config.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import razepl.dev.social365.auth.config.jwt.interfaces.JwtAuthenticationFilter;
import razepl.dev.social365.auth.config.jwt.interfaces.JwtService;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilterImpl extends OncePerRequestFilter implements JwtAuthenticationFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    public final void doFilterInternal(@NonNull HttpServletRequest request,
                                       @NonNull HttpServletResponse response,
                                       @NonNull FilterChain filterChain) throws ServletException, IOException {
        Optional<Jwt> token = jwtService.getJwtTokenFromRequest(request);

        token.ifPresent(authToken -> {
            Optional<String> usernameOptional = jwtService.getClaimFromToken(authToken.getTokenValue(), Claims::getSubject);

            usernameOptional.ifPresent(username -> setTokenForNotAuthenticatedUser(authToken, username, request));
        });

        filterChain.doFilter(request, response);
    }

    private void setTokenForNotAuthenticatedUser(Jwt jwtToken, String username, HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtService.isTokenNotValid(jwtToken.getTokenValue())) {
            return;
        }
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
                jwtToken,
                userDetails.getAuthorities()
        );
        jwtAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
    }

}
