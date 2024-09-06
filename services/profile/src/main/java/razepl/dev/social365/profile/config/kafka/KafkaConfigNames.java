package razepl.dev.social365.profile.config.kafka;

public final class KafkaConfigNames {

    public static final String PRODUCER_BOOTSTRAP_SERVERS = "${spring.kafka.producer.bootstrap-servers}";

    public static final String FRIENDSHIP_REQUESTED_TOPIC = "${kafka.topics.friendship-requested}";

    public static final String FRIENDSHIP_ACCEPTED_TOPIC = "${kafka.topics.friendship-accepted}";

    public static final String FRIENDSHIP_FOLLOWED_TOPIC = "${kafka.topics.friendship-followed}";

    public static final String FRIENDSHIP_REJECTED_TOPIC = "${kafka.topics.friendship-rejected}";

    private KafkaConfigNames() {
    }

}
