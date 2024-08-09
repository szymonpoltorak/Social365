package razepl.dev.social365.posts.api.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import razepl.dev.social365.posts.api.comments.data.CommentAddRequest;
import razepl.dev.social365.posts.api.comments.data.CommentEditRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.api.comments.interfaces.CommentService;
import razepl.dev.social365.posts.clients.images.ImageService;
import razepl.dev.social365.posts.config.User;
import razepl.dev.social365.posts.entities.comment.Comment;
import razepl.dev.social365.posts.entities.comment.CommentKey;
import razepl.dev.social365.posts.entities.comment.data.CommentKeyResponse;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentMapper;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentRepository;
import razepl.dev.social365.posts.entities.comment.reply.intefaces.ReplyCommentRepository;
import razepl.dev.social365.posts.utils.exceptions.CommentDoesNotExistException;
import razepl.dev.social365.posts.utils.exceptions.UserIsNotAuthorException;
import razepl.dev.social365.posts.utils.pagination.data.CommentsCassandraPage;
import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;
import razepl.dev.social365.posts.utils.validators.interfaces.CommentValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final CommentValidator commentValidator;
    private final ReplyCommentRepository replyCommentRepository;
    private final ImageService imageService;

    @Override
    public CassandraPage<CommentResponse> getCommentsForPost(String postId, String profileId, PageInfo pageInfo) {
        log.info("Getting comments for post with id: {}, with pageable: {}", postId, pageInfo);

        Slice<Comment> comments = commentRepository.findAllByPostId(UUID.fromString(postId), pageInfo.toPageable());

        log.info("Found {} comments for post with id: {}", comments.getSize(), postId);

        return CommentsCassandraPage.of(comments, comment -> commentMapper.toCommentResponse(comment, profileId));
    }

    @Override
    public CommentResponse addCommentToPost(User user, CommentAddRequest commentRequest) {
        log.info("Adding comment to post with id: {}, by profile: {}", commentRequest.postId(), user);

        Comment comment = Comment
                .builder()
                .key(CommentKey
                        .builder()
                        .commentId(UUID.randomUUID())
                        .creationDateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .postId(UUID.fromString(commentRequest.postId()))
                        .build()
                )
                .hasAttachments(commentRequest.hasAttachment())
                .authorId(user.profileId())
                .content(commentRequest.content())
                .hasReplies(false)
                .build();

        Comment savedComment = commentRepository.save(comment);

        log.info("Added comment with id: {}", savedComment.getCommentId());

        return commentMapper.toCommentResponseNoImage(savedComment, user.profileId());
    }

    @Override
    public CommentResponse editComment(User user, CommentEditRequest commentEditRequest) {
        log.info("Editing comment with data: {}, by user : {}", commentEditRequest, user);

        commentValidator.validateCommentRequest(commentEditRequest);

        CommentKey commentKey = commentMapper.toCommentKey(commentEditRequest.commentKey());
        Comment comment = getCommentFromRepository(commentKey);

        if (!comment.isAuthor(user.profileId())) {
            throw new UserIsNotAuthorException(user.profileId());
        }
        comment.setContent(commentEditRequest.content());

        log.info("Saving edited comment: {}", comment);

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toCommentResponse(savedComment, user.profileId());
    }

    @Override
    @Transactional
    public CommentResponse deleteComment(User user, CommentKeyResponse key) {
        log.info("Deleting comment with id: {}, by user : {}", key, user);

        CommentKey commentKey = commentMapper.toCommentKey(key);
        Comment comment = getCommentFromRepository(commentKey);
        String profileId = user.profileId();

        if (!comment.isAuthor(profileId)) {
            throw new UserIsNotAuthorException(profileId);
        }
        log.info("Deleting replies for comment with id: {}", comment.getCommentId());

        replyCommentRepository.deleteAllByCommentId(comment.getCommentId());

        imageService.deleteCommentImageById(comment.getCommentId().toString());

        log.info("Deleting comment...");

        commentRepository.deleteCommentByKey(commentKey);

        return CommentResponse.builder().build();
    }

    @Override
    public CommentResponse updateLikeCommentCount(User user, CommentKeyResponse key) {
        log.info("Updating like count for comment with id: {}, by user : {}", key, user);

        CommentKey commentKey = commentMapper.toCommentKey(key);

        Comment comment = getCommentFromRepository(commentKey);

        if (comment.isLikedBy(user.profileId())) {
            comment.getUserLikedIds().remove(user.profileId());
        } else {
            comment.addUserLikedId(user.profileId());
        }
        Comment savedComment = commentRepository.save(comment);

        log.info("Updated like count for comment with id: {}", savedComment.getCommentId());

        return CommentResponse.builder().build();
    }

    private Comment getCommentFromRepository(CommentKey key) {
        Comment comment = commentRepository.findCommentByKey(key)
                .orElseThrow(() -> new CommentDoesNotExistException(key));

        log.info("Found comment with id: {}", comment.getKey());

        return comment;
    }

}
