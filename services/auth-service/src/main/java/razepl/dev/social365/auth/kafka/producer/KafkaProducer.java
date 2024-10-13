package razepl.dev.social365.auth.kafka.producer;

import razepl.dev.social365.auth.entities.user.User;

public interface KafkaProducer {

    void sendAccountCreatedEvent(String username);

    void sendAccountLogoutEvent(User user);

}
