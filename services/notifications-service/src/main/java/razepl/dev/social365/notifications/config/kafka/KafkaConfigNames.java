package razepl.dev.social365.notifications.config.kafka;

public final class KafkaConfigNames {

    public static final String FRIENDSHIP_REQUESTED_TOPIC = "${kafka.topics.friendship-requested}";

    public static final String FRIENDSHIP_ACCEPTED_TOPIC = "${kafka.topics.friendship-accepted}";

    public static final String FRIENDSHIP_FOLLOWED_TOPIC = "${kafka.topics.friendship-followed}";

    public static final String FRIENDSHIP_REJECTED_TOPIC = "${kafka.topics.friendship-rejected}";

    public static final String POST_LIKED_TOPIC = "${kafka.topics.post-liked}";

    public static final String POST_COMMENTED_TOPIC = "${kafka.topics.post-commented}";

    public static final String COMMENT_LIKED_TOPIC = "${kafka.topics.comment-liked}";

    public static final String COMMENT_REPLIED_TOPIC = "${kafka.topics.comment-replied}";

    public static final String NOTIFICATIONS_GROUP_ID = "${kafka.group-ids.notifications}";

    private KafkaConfigNames() {
    }

}
