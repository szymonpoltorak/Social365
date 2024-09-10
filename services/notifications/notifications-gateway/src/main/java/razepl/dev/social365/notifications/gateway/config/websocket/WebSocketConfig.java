package razepl.dev.social365.notifications.gateway.config.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final String WEBSOCKET_TOPIC = "/topic";
    private static final String WEBSOCKET_APP = "/app";
    private static final String WEBSOCKET_ENDPOINT = "/notifications";
    private static final String WEBSOCKET_USER_PREFIX = "/user";

    private final ChannelInterceptor webSocketAuthInterceptor;

    @Override
    public final void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(WEBSOCKET_TOPIC, WEBSOCKET_USER_PREFIX);
        registry.setUserDestinationPrefix(WEBSOCKET_USER_PREFIX);
        registry.setApplicationDestinationPrefixes(WEBSOCKET_APP);
    }

    @Override
    public final void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint(WEBSOCKET_ENDPOINT)
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public final void configureClientInboundChannel(ChannelRegistration registration) {
        registration
                .interceptors(webSocketAuthInterceptor);
    }

}
