package razepl.dev.social365.posts.producer;

import razepl.dev.social365.posts.config.auth.User;
import razepl.dev.social365.posts.entities.comment.Comment;
import razepl.dev.social365.posts.entities.comment.reply.ReplyComment;
import razepl.dev.social365.posts.entities.post.Post;

public interface KafkaProducer {

    void sendPostLikedEvent(Post post, User likeAuthor, String targetProfileId);

    void sendPostCommentedEvent(Comment comment, User commentAuthor, String targetProfileId);

    void sendCommentRepliedEvent(ReplyComment comment, User replyAuthor, String targetProfileId);

    void sendCommentLikedEvent(Comment comment, User likeAuthor, String targetProfileId);

    void sendCommentLikedEvent(ReplyComment comment, User likeAuthor, String targetProfileId);

    void sendPostDeletedEvent(Post post, User author);

}
