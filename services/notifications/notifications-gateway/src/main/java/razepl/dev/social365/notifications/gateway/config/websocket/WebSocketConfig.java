package razepl.dev.social365.notifications.gateway.config.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final ChannelInterceptor webSocketAuthInterceptor;
    private final UserHandshakeHandler userHandshakeHandler;

    @Override
    public final void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(WebSocketMappings.WEBSOCKET_TOPIC);
//        registry.setUserDestinationPrefix(WebSocketMappings.USER_PREFIX);
        registry.setApplicationDestinationPrefixes(WebSocketMappings.WEBSOCKET_APP);
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        log.info("WebSocketConfig Headers : {}", headerAccessor.getMessageHeaders().entrySet());
        log.info("WebSocketConfig user : {}", headerAccessor.getUser());
        log.info("WebSocketConfig getAuthentication : {}", SecurityContextHolder.getContext().getAuthentication());
    }

    @Override
    public final void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint(WebSocketMappings.WEBSOCKET_NOTIFICATIONS_ENDPOINT)
                .setHandshakeHandler(userHandshakeHandler)
//                .addInterceptors(userHandshakeHandler)
                .setAllowedOrigins("http://localhost")
                .withSockJS();
    }

    @Override
    public final void configureClientInboundChannel(ChannelRegistration registration) {
        registration
                .interceptors(webSocketAuthInterceptor);
    }

}
