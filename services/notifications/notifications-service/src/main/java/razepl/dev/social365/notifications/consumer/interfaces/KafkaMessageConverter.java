package razepl.dev.social365.notifications.consumer.interfaces;

import razepl.dev.social365.notifications.consumer.FriendshipTopic;
import razepl.dev.social365.notifications.consumer.data.CommentLikedEvent;
import razepl.dev.social365.notifications.consumer.data.CommentRepliedEvent;
import razepl.dev.social365.notifications.consumer.data.FriendshipEvent;
import razepl.dev.social365.notifications.consumer.data.PostCommentedEvent;
import razepl.dev.social365.notifications.consumer.data.PostLikedEvent;

public interface KafkaMessageConverter {

    String convert(FriendshipEvent event, FriendshipTopic topic);

    String convert(CommentLikedEvent event);

    String convert(CommentRepliedEvent event);

    String convert(PostCommentedEvent event);

    String convert(PostLikedEvent event);

}
