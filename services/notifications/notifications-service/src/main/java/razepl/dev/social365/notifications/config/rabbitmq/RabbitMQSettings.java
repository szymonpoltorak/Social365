package razepl.dev.social365.notifications.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQSettings {

    public static final String NOTIFICATIONS_EXCHANGE_NAME = "notificationsExchange";

    public static final String NOTIFICATIONS_ROUTING_KEY = "notificationsKey";

    private static final String NOTIFICATIONS_QUEUE_NAME = "notifications";

    @Bean
    public Queue notificationsQueue() {
        return new Queue(NOTIFICATIONS_QUEUE_NAME);
    }

    @Bean
    public TopicExchange notificationsExchange() {
        return new TopicExchange(NOTIFICATIONS_EXCHANGE_NAME);
    }

    @Bean
    public Binding notificationsBinding(Queue notificationsQueue, TopicExchange notificationsExchange) {
        return BindingBuilder.bind(notificationsQueue).to(notificationsExchange).with(NOTIFICATIONS_ROUTING_KEY);
    }

}
