package razepl.dev.social365.notifications.config.kafka;

public final class KafkaConfigNames {

    public static final String CONSUMER_BOOTSTRAP_SERVERS = "${spring.kafka.consumer.bootstrap-servers}";

    public static final String FRIENDSHIP_REQUESTED_TOPIC = "${kafka.topics.friendship-requested}";

    public static final String FRIENDSHIP_ACCEPTED_TOPIC = "${kafka.topics.friendship-accepted}";

    public static final String FRIENDSHIP_FOLLOWED_TOPIC = "${kafka.topics.friendship-followed}";

    public static final String FRIENDSHIP_REJECTED_TOPIC = "${kafka.topics.friendship-rejected}";

    public static final String POST_LIKED_TOPIC = "${kafka.topics.post-liked}";

    public static final String POST_COMMENTED_TOPIC = "${kafka.topics.post-commented}";

    public static final String COMMENT_LIKED_TOPIC = "${kafka.topics.comment-liked}";

    public static final String COMMENT_REPLIED_TOPIC = "${kafka.topics.comment-replied}";

    public static final String NOTIFICATIONS_GROUP_ID = "${kafka.group-ids.notifications}";

    public static final String FRIENDSHIP_EVENT_TYPE_PROPERTY = "spring.json.value.default.type=razepl.dev.social365.notifications.consumer.data.FriendshipEvent";

    public static final String FRIENDSHIP_REJECTED_EVENT_TYPE_PROPERTY = "spring.json.value.default.type=razepl.dev.social365.notifications.consumer.data.FriendshipRejectedEvent";

    public static final String POST_LIKED_EVENT_TYPE_PROPERTY = "spring.json.value.default.type=razepl.dev.social365.notifications.consumer.data.PostLikedEvent";

    public static final String POST_COMMENTED_EVENT_TYPE_PROPERTY = "spring.json.value.default.type=razepl.dev.social365.notifications.consumer.data.PostCommentedEvent";

    public static final String COMMENT_LIKED_EVENT_TYPE_PROPERTY = "spring.json.value.default.type=razepl.dev.social365.notifications.consumer.data.CommentLikedEvent";

    public static final String COMMENT_REPLIED_EVENT_TYPE_PROPERTY = "spring.json.value.default.type=razepl.dev.social365.notifications.consumer.data.CommentRepliedEvent";

    private KafkaConfigNames() {
    }

}
