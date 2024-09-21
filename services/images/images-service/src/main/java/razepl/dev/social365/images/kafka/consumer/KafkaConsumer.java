package razepl.dev.social365.images.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import razepl.dev.social365.images.api.interfaces.FileManagementService;
import razepl.dev.social365.images.config.kafka.KafkaConfigNames;
import razepl.dev.social365.images.entities.image.comment.interfaces.CommentImageRepository;
import razepl.dev.social365.images.entities.image.post.interfaces.PostImagesRepository;
import razepl.dev.social365.images.kafka.consumer.data.AccountCreatedEvent;
import razepl.dev.social365.images.kafka.consumer.data.PostDeletedEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final FileManagementService fileManagementService;
    private final PostImagesRepository postImagesRepository;
    private final CommentImageRepository commentImageRepository;

    @KafkaListener(
            topics = KafkaConfigNames.ACCOUNT_CREATED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.ACCOUNT_CREATED_EVENT_DATA
    )
    public void consumeAccountCreatedEvent(AccountCreatedEvent accountCreatedEvent) {
        log.info("Consumed account.created event: {}", accountCreatedEvent);

        fileManagementService.createProfileFolder(accountCreatedEvent.username());
    }

    @Transactional
    @KafkaListener(
            topics = KafkaConfigNames.POST_DELETED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.POST_DELETED_EVENT_DATA
    )
    public void consumePostDeletedEvent(PostDeletedEvent postDeletedEvent) {
        log.info("Consumed post.deleted event: {}", postDeletedEvent);

        postImagesRepository.deleteAllByPostId(postDeletedEvent.postId());

        commentImageRepository.deleteAllByPostId(postDeletedEvent.postId());
    }

}
