package razepl.dev.social365.images.config.kafka;

public final class KafkaConfigNames {

    public static final String CONSUMER_BOOTSTRAP_SERVERS = "${spring.kafka.consumer.bootstrap-servers}";

    public static final String ACCOUNT_CREATED_TOPIC = "${kafka.topics.account-created}";

    public static final String NOTIFICATIONS_GROUP_ID = "${kafka.group-ids.notifications}";

    public static final String ACCOUNT_CREATED_EVENT_DATA = "spring.json.value.default.type=razepl.dev.social365.images.kafka.consumer.data.AccountCreatedEvent";

    public static final String POST_DELETED_TOPIC = "${kafka.topics.post-deleted}";

    public static final String POST_DELETED_EVENT_DATA = "spring.json.value.default.type=razepl.dev.social365.images.kafka.consumer.data.PostDeletedEvent";

    private KafkaConfigNames() {
    }

}
