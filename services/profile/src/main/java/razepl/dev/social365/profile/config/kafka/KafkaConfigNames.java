package razepl.dev.social365.profile.config.kafka;

public final class KafkaConfigNames {

    public static final String PRODUCER_BOOTSTRAP_SERVERS = "${spring.kafka.producer.bootstrap-servers}";

    public static final String FRIENDSHIP_REQUESTED_TOPIC = "${kafka.topics.friendship-requested}";

    public static final String FRIENDSHIP_ACCEPTED_TOPIC = "${kafka.topics.friendship-accepted}";

    public static final String FRIENDSHIP_FOLLOWED_TOPIC = "${kafka.topics.friendship-followed}";

    public static final String FRIENDSHIP_REJECTED_TOPIC = "${kafka.topics.friendship-rejected}";

    public static final String ACCOUNT_LOGOUT_TOPIC = "${kafka.topics.account-logout}";

    public static final String NOTIFICATIONS_GROUP_ID = "${kafka.group-ids.notifications}";

    public static final String ACCOUNT_LOGOUT_EVENT_TYPE_PROPERTY = "spring.json.value.default.type=razepl.dev.social365.profile.kafka.consumer.AccountLogoutEvent";

    private KafkaConfigNames() {
    }

}
