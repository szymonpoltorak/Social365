package razepl.dev.social365.posts.api.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import razepl.dev.social365.posts.api.comments.data.CommentAddRequest;
import razepl.dev.social365.posts.api.comments.data.CommentDeleteRequest;
import razepl.dev.social365.posts.api.comments.data.CommentEditRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.api.comments.data.LikeCommentRequest;
import razepl.dev.social365.posts.api.comments.interfaces.CommentService;
import razepl.dev.social365.posts.entities.comment.Comment;
import razepl.dev.social365.posts.entities.comment.CommentKey;
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

    @Override
    public final CassandraPage<CommentResponse> getCommentsForPost(String postId, String profileId, PageInfo pageInfo) {
        log.info("Getting comments for post with id: {}, with pageable: {}", postId, pageInfo);

        Slice<Comment> comments = commentRepository.findAllByPostId(UUID.fromString(postId), pageInfo.toPageable());

        log.info("Found {} comments for post with id: {}", comments.getSize(), postId);

        return CommentsCassandraPage.of(comments, comment -> commentMapper.toCommentResponse(comment, profileId));
    }

    @Override
    public final CommentResponse addCommentToPost(CommentAddRequest commentRequest) {
        log.info("Adding comment to post with id: {}, by profile: {}", commentRequest.postId(),
                commentRequest.profileId());

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
                .authorId(commentRequest.profileId())
                .content(commentRequest.content())
                .hasReplies(false)
                .build();

        Comment savedComment = commentRepository.save(comment);

        log.info("Added comment with id: {}", savedComment.getCommentId());

        return commentMapper.toCommentResponse(savedComment, commentRequest.profileId());
    }

    @Override
    public final CommentResponse editComment(CommentEditRequest commentEditRequest) {
        log.info("Editing comment with id: {}, with content: {}", commentEditRequest.commentKey(),
                commentEditRequest.content());

        commentValidator.validateCommentRequest(commentEditRequest);

        CommentKey commentKey = commentMapper.toCommentKey(commentEditRequest.commentKey());
        Comment comment = getCommentFromRepository(commentKey);

        if (!comment.isAuthor(commentEditRequest.profileId())) {
            throw new UserIsNotAuthorException(commentEditRequest.profileId());
        }
        comment.setContent(commentEditRequest.content());

        log.info("Saving edited comment with id: {}", comment.getCommentId());

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toCommentResponse(savedComment, commentEditRequest.profileId());
    }

    @Override
    public final CommentResponse deleteComment(CommentDeleteRequest commentRequest) {
        log.info("Deleting comment with id: {}", commentRequest.commentKey());

        CommentKey commentKey = commentMapper.toCommentKey(commentRequest.commentKey());
        Comment comment = getCommentFromRepository(commentKey);
        String profileId = commentRequest.profileId();

        if (!comment.isAuthor(profileId)) {
            throw new UserIsNotAuthorException(profileId);
        }
        log.info("Deleting comment...");

        commentRepository.deleteCommentByKey(commentKey);

        return CommentResponse.builder().build();
    }

    @Override
    public final CommentResponse updateLikeCommentCount(LikeCommentRequest likeCommentRequest) {
        log.info("Updating like count for comment with id: {}", likeCommentRequest.commentKey());

        CommentKey commentKey = commentMapper.toCommentKey(likeCommentRequest.commentKey());

        Comment comment = getCommentFromRepository(commentKey);

        if (comment.isLikedBy(likeCommentRequest.profileId())) {
            comment.getUserLikedIds().remove(likeCommentRequest.profileId());
        } else {
            comment.addUserLikedId(likeCommentRequest.profileId());
        }
        Comment savedComment = commentRepository.save(comment);

        log.info("Updated like count for comment with id: {}", savedComment.getCommentId());

        return commentMapper.toCommentResponse(savedComment, likeCommentRequest.profileId());
    }

    private Comment getCommentFromRepository(CommentKey key) {
        Comment comment = commentRepository.findCommentByKey(key)
                .orElseThrow(() -> new CommentDoesNotExistException(key));

        log.info("Found comment with id: {}", comment.getKey());

        return comment;
    }

}
