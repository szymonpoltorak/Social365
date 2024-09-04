package razepl.dev.social365.notifications.config.kafka;

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
            @Value(KafkaConfigNames.FRIENDSHIP_ACCEPTED_TOPIC) String friendshipAcceptedTopic,
            @Value(KafkaConfigNames.FRIENDSHIP_FOLLOWED_TOPIC) String friendshipFollowedTopic,
            @Value(KafkaConfigNames.FRIENDSHIP_REJECTED_TOPIC) String friendshipRejectedTopic,
            @Value(KafkaConfigNames.FRIENDSHIP_REQUESTED_TOPIC) String friendshipRequestedTopic,
            @Value(KafkaConfigNames.POST_COMMENTED_TOPIC) String postCommentedTopic,
            @Value(KafkaConfigNames.POST_LIKED_TOPIC) String postLikedTopic,
            @Value(KafkaConfigNames.COMMENT_LIKED_TOPIC) String commentLikedTopic,
            @Value(KafkaConfigNames.COMMENT_REPLIED_TOPIC) String commentRepliedTopic
    ) {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(friendshipAcceptedTopic).build(),
                TopicBuilder.name(friendshipFollowedTopic).build(),
                TopicBuilder.name(friendshipRejectedTopic).build(),
                TopicBuilder.name(friendshipRequestedTopic).build(),
                TopicBuilder.name(postCommentedTopic).build(),
                TopicBuilder.name(postLikedTopic).build(),
                TopicBuilder.name(commentLikedTopic).build(),
                TopicBuilder.name(commentRepliedTopic).build()
        );
    }

}
