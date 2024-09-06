package razepl.dev.social365.profile.producer;

import razepl.dev.social365.profile.config.auth.User;
import razepl.dev.social365.profile.nodes.profile.Profile;

public interface KafkaProducer {

    void sendFriendshipAcceptedEvent(Profile targetProfile, User sourceProfile);

    void sendFriendshipRequestedEvent(Profile targetProfile, User sourceProfile);

    void sendFriendshipFollowedEvent(Profile targetProfile, User sourceProfile);

    void sendFriendshipRejectedEvent(Profile targetProfile, User sourceProfile);

}
