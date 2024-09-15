package razepl.dev.social365.notifications.documents;

import razepl.dev.social365.notifications.api.notifications.data.NotificationResponse;
import razepl.dev.social365.notifications.consumer.data.CommentLikedEvent;
import razepl.dev.social365.notifications.consumer.data.CommentRepliedEvent;
import razepl.dev.social365.notifications.consumer.data.PostCommentedEvent;
import razepl.dev.social365.notifications.consumer.data.PostLikedEvent;
import razepl.dev.social365.notifications.documents.posts.comments.CommentLikedNotification;
import razepl.dev.social365.notifications.documents.posts.comments.CommentRepliedNotification;
import razepl.dev.social365.notifications.documents.posts.comments.PostCommentedNotification;
import razepl.dev.social365.notifications.documents.posts.comments.PostLikedNotification;

public interface NotificationsMapper {

    NotificationResponse mapNotificationToNotificationResponse(Notification notification);

    PostLikedNotification mapPostLikedEventToPostLikedNotification(PostLikedEvent event);

    PostCommentedNotification mapPostCommentedEventToPostCommentedNotification(PostCommentedEvent event);

    CommentLikedNotification mapCommentLikedEventToCommentLikedNotification(CommentLikedEvent event);

    CommentRepliedNotification mapCommentRepliedEventToCommentRepliedNotification(CommentRepliedEvent event);

}
