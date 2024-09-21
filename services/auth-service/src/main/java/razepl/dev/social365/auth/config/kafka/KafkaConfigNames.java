package razepl.dev.social365.auth.config.kafka;

public final class KafkaConfigNames {

    static final String PRODUCER_BOOTSTRAP_SERVERS = "${spring.kafka.producer.bootstrap-servers}";

    public static final String ACCOUNT_CREATED_TOPIC = "${kafka.topics.account-created}";

    public static final String NOTIFICATIONS_GROUP_ID = "${kafka.group-ids.notifications}";

    private KafkaConfigNames() {
    }

}
