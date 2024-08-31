package razepl.dev.social365.notifications.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import razepl.dev.social365.notifications.config.kafka.KafkaConfigNames;
import razepl.dev.social365.notifications.consumer.data.CommentLikedEvent;
import razepl.dev.social365.notifications.consumer.data.CommentRepliedEvent;
import razepl.dev.social365.notifications.consumer.data.FriendshipEvent;
import razepl.dev.social365.notifications.consumer.data.FriendshipRejectedEvent;
import razepl.dev.social365.notifications.consumer.data.PostCommentedEvent;
import razepl.dev.social365.notifications.consumer.data.PostLikedEvent;

@Slf4j
@Service
public class NotificationsConsumer {

    @KafkaListener(
            topics = KafkaConfigNames.FRIENDSHIP_REQUESTED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.FRIENDSHIP_EVENT_TYPE_PROPERTY
    )
    public final void consumeFriendshipRequested(FriendshipEvent event) {
        log.info("Consumed friendship.requested event: {}", event);
    }

    @KafkaListener(
            topics = KafkaConfigNames.FRIENDSHIP_ACCEPTED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.FRIENDSHIP_EVENT_TYPE_PROPERTY
    )
    public final void consumeFriendshipAccepted(FriendshipEvent event) {
        log.info("Consumed friendship.accepted event: {}", event);
    }

    @KafkaListener(
            topics = KafkaConfigNames.FRIENDSHIP_FOLLOWED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.FRIENDSHIP_EVENT_TYPE_PROPERTY
    )
    public final void consumeFriendshipFollowed(FriendshipEvent event) {
        log.info("Consumed friendship.followed event: {}", event);
    }

    @KafkaListener(
            topics = KafkaConfigNames.FRIENDSHIP_REJECTED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.FRIENDSHIP_REJECTED_EVENT_TYPE_PROPERTY
    )
    public final void consumeFriendshipRejected(FriendshipRejectedEvent event) {
        log.info("Consumed friendship.rejected event: {}", event);
    }

    @KafkaListener(
            topics = KafkaConfigNames.POST_LIKED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.POST_LIKED_EVENT_TYPE_PROPERTY
    )
    public final void consumePostLiked(PostLikedEvent event) {
        log.info("Consumed post.liked event: {}", event);
    }

    @KafkaListener(
            topics = KafkaConfigNames.POST_COMMENTED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.POST_COMMENTED_EVENT_TYPE_PROPERTY
    )
    public final void consumePostCommented(PostCommentedEvent event) {
        log.info("Consumed post.commented event: {}", event);
    }

    @KafkaListener(
            topics = KafkaConfigNames.COMMENT_LIKED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.COMMENT_LIKED_EVENT_TYPE_PROPERTY
    )
    public final void consumeCommentLiked(CommentLikedEvent event) {
        log.info("Consumed comment.liked event: {}", event);
    }

    @KafkaListener(
            topics = KafkaConfigNames.COMMENT_REPLIED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.COMMENT_REPLIED_EVENT_TYPE_PROPERTY
    )
    public final void consumeCommentReplied(CommentRepliedEvent event) {
        log.info("Consumed comment.replied event: {}", event);
    }

}
