package razepl.dev.social365.notifications.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import razepl.dev.social365.notifications.config.kafka.KafkaConfigNames;
import razepl.dev.social365.notifications.consumer.data.FriendshipEvent;

@Slf4j
@Service
public class NotificationsConsumer {

    @KafkaListener(topics = KafkaConfigNames.FRIENDSHIP_REQUESTED_TOPIC, groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID)
    public final void consumeFriendshipRequested(FriendshipEvent event) {
        log.info("Consumed friendship.requested event: {}", event);
    }

    @KafkaListener(topics = KafkaConfigNames.FRIENDSHIP_ACCEPTED_TOPIC, groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID)
    public final void consumeFriendshipAccepted(FriendshipEvent event) {
        log.info("Consumed friendship.accepted event: {}", event);
    }

    @KafkaListener(topics = KafkaConfigNames.FRIENDSHIP_FOLLOWED_TOPIC, groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID)
    public final void consumeFriendshipFollowed(FriendshipEvent event) {
        log.info("Consumed friendship.followed event: {}", event);
    }

    @KafkaListener(topics = KafkaConfigNames.FRIENDSHIP_REJECTED_TOPIC, groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID)
    public final void consumeFriendshipRejected(FriendshipEvent event) {
        log.info("Consumed friendship.rejected event: {}", event);
    }

    @KafkaListener(topics = KafkaConfigNames.POST_LIKED_TOPIC, groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID)
    public final void consumePostLiked(FriendshipEvent event) {
        log.info("Consumed post.liked event: {}", event);
    }

    @KafkaListener(topics = KafkaConfigNames.POST_COMMENTED_TOPIC, groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID)
    public final void consumePostCommented(FriendshipEvent event) {
        log.info("Consumed post.commented event: {}", event);
    }

    @KafkaListener(topics = KafkaConfigNames.COMMENT_LIKED_TOPIC, groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID)
    public final void consumeCommentLiked(FriendshipEvent event) {
        log.info("Consumed comment.liked event: {}", event);
    }

    @KafkaListener(topics = KafkaConfigNames.COMMENT_REPLIED_TOPIC, groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID)
    public final void consumeCommentReplied(FriendshipEvent event) {
        log.info("Consumed comment.replied event: {}", event);
    }

}
