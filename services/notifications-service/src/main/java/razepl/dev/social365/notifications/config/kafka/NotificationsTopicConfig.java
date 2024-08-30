package razepl.dev.social365.notifications.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@Configuration
public class NotificationsTopicConfig {

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = Map.of(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-broker:9092",
                AdminClientConfig.SECURITY_PROTOCOL_CONFIG, "PLAINTEXT"
        );
        KafkaAdmin admin = new KafkaAdmin(configs);

        admin.setAutoCreate(true);

        return admin;
    }


    @Bean
    public NewTopic notificationsTopic(@Value("${spring.kafka.consumer.topic") String topicName) {
        return TopicBuilder
                .name(topicName)
                .build();
    }

}
