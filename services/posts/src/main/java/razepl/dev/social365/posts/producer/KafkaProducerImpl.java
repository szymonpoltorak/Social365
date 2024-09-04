package razepl.dev.social365.posts.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import razepl.dev.social365.posts.clients.profile.ProfileService;
import razepl.dev.social365.posts.clients.profile.data.Profile;
import razepl.dev.social365.posts.config.auth.User;
import razepl.dev.social365.posts.config.kafka.KafkaConfigNames;
import razepl.dev.social365.posts.entities.comment.Comment;
import razepl.dev.social365.posts.entities.comment.reply.ReplyComment;
import razepl.dev.social365.posts.entities.post.Post;
import razepl.dev.social365.posts.producer.data.CommentLikedEvent;
import razepl.dev.social365.posts.producer.data.CommentRepliedEvent;
import razepl.dev.social365.posts.producer.data.PostCommentedEvent;
import razepl.dev.social365.posts.producer.data.PostLikedEvent;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ProfileService profileService;
    private final ObjectMapper jsonMapper;
    private final String postLikedTopic;
    private final String postCommentedTopic;
    private final String commentLikedTopic;
    private final String commentRepliedTopic;

    public KafkaProducerImpl(KafkaTemplate<String, String> kafkaTemplate, ProfileService profileService, ObjectMapper jsonMapper,
                             @Value(KafkaConfigNames.POST_LIKED_TOPIC) String postLikedTopic,
                             @Value(KafkaConfigNames.POST_COMMENTED_TOPIC) String postCommentedTopic,
                             @Value(KafkaConfigNames.COMMENT_LIKED_TOPIC) String commentLikedTopic,
                             @Value(KafkaConfigNames.COMMENT_REPLIED_TOPIC) String commentRepliedTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.profileService = profileService;
        this.jsonMapper = jsonMapper;
        this.postLikedTopic = postLikedTopic;
        this.postCommentedTopic = postCommentedTopic;
        this.commentLikedTopic = commentLikedTopic;
        this.commentRepliedTopic = commentRepliedTopic;
    }

    @Override
    public final void sendPostLikedEvent(Post post, User likeAuthor, String targetProfileId) {
        Profile profile = profileService.getProfileDetails(likeAuthor.profileId());
        PostLikedEvent postLikedEvent = PostLikedEvent
                .builder()
                .eventId(UUID.randomUUID().toString())
                .timeStamp(Instant.now().toString())
                .targetProfileId(targetProfileId)
                .likeAuthorFullName(profile.fullName())
                .sourceProfileId(likeAuthor.profileId())
                .currentNumOfLikes(post.getLikesCount())
                .build();

        log.info("Sending post liked event: {}", postLikedEvent);

        sendMessage(postLikedTopic, postLikedEvent);
    }

    @Override
    public final void sendPostCommentedEvent(Comment comment, User commentAuthor, String targetProfileId) {
        Profile profile = profileService.getProfileDetails(commentAuthor.profileId());
        PostCommentedEvent postCommentedEvent = PostCommentedEvent
                .builder()
                .eventId(UUID.randomUUID().toString())
                .timeStamp(Instant.now().toString())
                .targetProfileId(targetProfileId)
                .commentAuthorFullName(profile.fullName())
                .sourceProfileId(commentAuthor.profileId())
                .commentId(comment.getCommentId().toString())
                .postId(comment.getPostId().toString())
                .build();

        log.info("Sending post commented event: {}", postCommentedEvent);

        sendMessage(postCommentedTopic, postCommentedEvent);
    }

    @Override
    public final void sendCommentRepliedEvent(ReplyComment comment, User replyAuthor, String targetProfileId) {
        Profile profile = profileService.getProfileDetails(replyAuthor.profileId());
        CommentRepliedEvent commentRepliedEvent = CommentRepliedEvent
                .builder()
                .eventId(UUID.randomUUID().toString())
                .timeStamp(Instant.now().toString())
                .targetProfileId(targetProfileId)
                .replyAuthorFullName(profile.fullName())
                .sourceProfileId(replyAuthor.profileId())
                .commentId(comment.getCommentId().toString())
                .replyCommentId(comment.getReplyCommentId().toString())
                .build();

        log.info("Sending comment replied event: {}", commentRepliedEvent);

        sendMessage(commentRepliedTopic, commentRepliedEvent);
    }

    @Override
    public final void sendCommentLikedEvent(Comment comment, User likeAuthor, String targetProfileId) {
        Profile profile = profileService.getProfileDetails(likeAuthor.profileId());
        CommentLikedEvent postLikedEvent = CommentLikedEvent
                .builder()
                .commentId(comment.getCommentId().toString())
                .postId(comment.getPostId().toString())
                .eventId(UUID.randomUUID().toString())
                .timeStamp(Instant.now().toString())
                .targetProfileId(targetProfileId)
                .likeAuthorFullName(profile.fullName())
                .sourceProfileId(likeAuthor.profileId())
                .currentNumOfLikes(comment.getLikesCount())
                .build();

        log.info("Sending comment liked event: {}", postLikedEvent);

        sendMessage(commentLikedTopic, postLikedEvent);

    }

    @Override
    public final void sendCommentLikedEvent(ReplyComment comment, User likeAuthor, String targetProfileId) {
        Profile profile = profileService.getProfileDetails(likeAuthor.profileId());
        CommentLikedEvent postLikedEvent = CommentLikedEvent
                .builder()
                .commentId(comment.getReplyCommentId().toString())
                .eventId(UUID.randomUUID().toString())
                .timeStamp(Instant.now().toString())
                .targetProfileId(targetProfileId)
                .likeAuthorFullName(profile.fullName())
                .sourceProfileId(likeAuthor.profileId())
                .currentNumOfLikes(comment.getLikesCount())
                .build();

        log.info("Sending reply comment liked event: {}", postLikedEvent);

        sendMessage(commentLikedTopic, postLikedEvent);
    }

    private void sendMessage(String topic, Object event) {
        try {
            String eventJson = jsonMapper.writeValueAsString(event);

            kafkaTemplate.send(topic, eventJson);

        } catch (JsonProcessingException exception) {
            log.error("Failed to convert event to JSON: {}", event, exception);
        }
    }
}
