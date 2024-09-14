package razepl.dev.social365.notifications.gateway.config.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final JwtDecoder jwtDecoder;

    @Override
    public final Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//
//        if (StompCommand.CONNECT != accessor.getCommand()) {
//            return message;
//        }
//        String token = accessor.getFirstNativeHeader("Authorization");
//
//        log.info("Authorization header: {}", token);
//
//        if (token == null) {
//            return message;
//        }
//        Jwt jwt = jwtDecoder.decode(token);
//
//        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt));

        return message;
    }

    @Override
    public final void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        log.info("afterSendCompletion Message: {}", message);
        log.info("afterSendCompletion Channel: {}", channel);
        log.info("afterSendCompletion Sent: {}", sent);
        log.info("afterSendCompletion Exception: {}", ex);
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        log.info("postSend Message: {}", message);
        log.info("postSend Channel: {}", channel);
        log.info("postSend Sent: {}", sent);
    }

    @Override
    public boolean preReceive(MessageChannel mc) {
        log.info("In preReceive");
        return true;
    }

}
