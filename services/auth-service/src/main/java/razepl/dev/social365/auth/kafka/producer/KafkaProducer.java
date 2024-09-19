package razepl.dev.social365.auth.kafka.producer;

@FunctionalInterface
public interface KafkaProducer {

    void sendAccountCreatedEvent(String username);

}
