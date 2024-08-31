package razepl.dev.social365.notifications.consumer;

import org.springframework.stereotype.Service;
import razepl.dev.social365.notifications.consumer.data.CommentLikedEvent;
import razepl.dev.social365.notifications.consumer.data.CommentRepliedEvent;
import razepl.dev.social365.notifications.consumer.data.FriendshipEvent;
import razepl.dev.social365.notifications.consumer.data.FriendshipRejectedEvent;
import razepl.dev.social365.notifications.consumer.data.PostCommentedEvent;
import razepl.dev.social365.notifications.consumer.data.PostLikedEvent;
import razepl.dev.social365.notifications.consumer.interfaces.KafkaMessageConverter;

@Service
public class KafkaMessageConverterImpl implements KafkaMessageConverter {

    @Override
    public final String convert(FriendshipEvent event, FriendshipTopic topic) {
        return switch (topic) {
            case ACCEPTED -> "%s accepted your friend request".formatted(event.friendFullName());
            case FOLLOWED -> "%s follows you now".formatted(event.friendFullName());
            case REQUESTED -> "%s sent you a friend request".formatted(event.friendFullName());
        };
    }

    @Override
    public final String convert(FriendshipRejectedEvent event) {
        return "";
    }

    @Override
    public final String convert(CommentLikedEvent event) {
        return "";
    }

    @Override
    public final String convert(CommentRepliedEvent event) {
        return "";
    }

    @Override
    public final String convert(PostCommentedEvent event) {
        return "";
    }

    @Override
    public final String convert(PostLikedEvent event) {
        return "";
    }

}
