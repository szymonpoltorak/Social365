package razepl.dev.social365.profile.kafka.producer;

import razepl.dev.social365.profile.nodes.profile.Profile;

public interface KafkaProducer {

    void sendFriendshipAcceptedEvent(Profile targetProfile, Profile sourceProfile);

    void sendFriendshipRequestedEvent(Profile targetProfile, Profile sourceProfile);

    void sendFriendshipFollowedEvent(Profile targetProfile, Profile sourceProfile);

    void sendFriendshipRejectedEvent(Profile targetProfile, Profile sourceProfile);

}
