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
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

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
        log.info("Headers : {}", request.getHeaders().entrySet());
        log.info("Attributes : {}", attributes);
        log.info("Principal : {}", request.getPrincipal());
        log.info("Security Context : {}", SecurityContextHolder.getContext().getAuthentication());

        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String token = servletRequest.getParameter("token");

        log.info("Token param: {}", token);

        if (token != null) {
            Jwt jwtToken = jwtDecoder.decode(token);

            log.info("JWT Token: {}", Optional.ofNullable(jwtToken.getClaim("profileId")));

            return new UserPrincipal(jwtToken.getClaim("profileId"));
        }

        return SecurityContextHolder.getContext().getAuthentication();
    }

//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
//        String token = servletRequest.getParameter("token");
//        log.info("Token param: {}", token);
//
//        if (token != null) {
//            Jwt jwtToken = jwtDecoder.decode(token);
//
//            // Decode JWT and extract profileId
//            String profileId = jwtToken.getClaim("profileId");
//
//            // Store profileId in WebSocket session attributes
//            attributes.put("profileId", profileId);
//        }
//
//        return true;
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//
//    }
}
