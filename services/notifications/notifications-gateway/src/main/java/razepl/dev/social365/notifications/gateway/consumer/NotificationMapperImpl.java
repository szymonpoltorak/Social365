package razepl.dev.social365.notifications.gateway.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import razepl.dev.social365.notifications.gateway.consumer.data.NotificationResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationMapperImpl implements NotificationMapper {

    private final ObjectMapper jsonMapper;

    @Override
    public final NotificationResponse mapNotification(String notification) {
        try {
            return jsonMapper.readValue(notification, NotificationResponse.class);

        } catch (JsonProcessingException e) {
            log.error("Error while mapping notification to response", e);

            throw new IllegalStateException("Error while mapping notification", e);
        }
    }
}
