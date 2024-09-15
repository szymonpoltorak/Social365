package razepl.dev.social365.notifications.gateway.config.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final UserHandshakeHandler userHandshakeHandler;

    @Override
    public final void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(WebSocketMappings.WEBSOCKET_TOPIC);
        registry.setApplicationDestinationPrefixes(WebSocketMappings.WEBSOCKET_APP);
    }

    @Override
    public final void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint(WebSocketMappings.WEBSOCKET_NOTIFICATIONS_ENDPOINT)
                .setHandshakeHandler(userHandshakeHandler)
                .setAllowedOrigins("http://localhost")
                .withSockJS();
    }

}
