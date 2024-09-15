package razepl.dev.social365.notifications.gateway.config.websocket;

import com.sun.security.auth.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import razepl.dev.social365.notifications.gateway.config.auth.JwtClaims;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHandshakeHandler extends DefaultHandshakeHandler {

    private final JwtDecoder jwtDecoder;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String token = servletRequest.getParameter("token");

        log.info("Token param: {}", token);

        if (token != null) {
            Jwt jwtToken = jwtDecoder.decode(token);

            log.info("JWT Token: {}", Optional.ofNullable(jwtToken.getClaim(JwtClaims.PROFILE_ID)));

            return new UserPrincipal(jwtToken.getClaim(JwtClaims.PROFILE_ID));
        }

        return SecurityContextHolder.getContext().getAuthentication();
    }

}
