package razepl.dev.social365.images.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@Configuration
public class NotificationsTopicConfig {

    @Bean
    public KafkaAdmin admin(@Value(KafkaConfigNames.CONSUMER_BOOTSTRAP_SERVERS) String bootstrapServers) {
        Map<String, Object> configs = Map.of(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                AdminClientConfig.SECURITY_PROTOCOL_CONFIG, "PLAINTEXT"
        );
        KafkaAdmin admin = new KafkaAdmin(configs);

        admin.setAutoCreate(true);

        return admin;
    }

    @Bean
    public KafkaAdmin.NewTopics kafkaTopics(
            @Value(KafkaConfigNames.ACCOUNT_CREATED_TOPIC) String accountCreated
    ) {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(accountCreated).build()
        );
    }

}