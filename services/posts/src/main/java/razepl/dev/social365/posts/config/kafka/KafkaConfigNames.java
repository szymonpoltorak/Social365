package razepl.dev.social365.posts.config.kafka;

public final class KafkaConfigNames {

    public static final String POST_DELETED_TOPIC = "${kafka.topics.post-deleted}";

    static final String PRODUCER_BOOTSTRAP_SERVERS = "${spring.kafka.producer.bootstrap-servers}";

    public static final String POST_LIKED_TOPIC = "${kafka.topics.post-liked}";

    public static final String POST_COMMENTED_TOPIC = "${kafka.topics.post-commented}";

    public static final String COMMENT_LIKED_TOPIC = "${kafka.topics.comment-liked}";

    public static final String COMMENT_REPLIED_TOPIC = "${kafka.topics.comment-replied}";

    public static final String NOTIFICATIONS_GROUP_ID = "${kafka.group-ids.notifications}";

    private KafkaConfigNames() {
    }

}
