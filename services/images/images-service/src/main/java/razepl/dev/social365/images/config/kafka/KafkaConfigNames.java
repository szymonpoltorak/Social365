package razepl.dev.social365.images.config.kafka;

public final class KafkaConfigNames {

    public static final String CONSUMER_BOOTSTRAP_SERVERS = "${spring.kafka.consumer.bootstrap-servers}";

    public static final String ACCOUNT_CREATED_TOPIC = "${kafka.topics.account-created}";

    public static final String NOTIFICATIONS_GROUP_ID = "${kafka.group-ids.notifications}";

    private KafkaConfigNames() {
    }

}
