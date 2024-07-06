package razepl.dev.social365.posts.api.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import razepl.dev.social365.posts.api.comments.data.CommentAddRequest;
import razepl.dev.social365.posts.api.comments.data.CommentDeleteRequest;
import razepl.dev.social365.posts.api.comments.data.CommentRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.api.comments.interfaces.CommentService;
import razepl.dev.social365.posts.entities.comment.Comment;
import razepl.dev.social365.posts.entities.comment.CommentKey;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentMapper;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentRepository;
import razepl.dev.social365.posts.utils.exceptions.CommentDoesNotExistException;
import razepl.dev.social365.posts.utils.exceptions.UserIsNotAuthorException;
import razepl.dev.social365.posts.utils.pagination.data.CommentsCassandraPage;
import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;
import razepl.dev.social365.posts.utils.pagination.interfaces.PagingState;
import razepl.dev.social365.posts.utils.validators.interfaces.CommentValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final CommentValidator commentValidator;

    @Override
    //TODO: Fix replies to be a separate table
    public final CassandraPage<CommentResponse> getRepliesForComment(String commentId, String profileId, PageInfo pageInfo) {
        log.info("Getting replies for comment with id: {}, with pageable: {}", commentId, pageInfo);

        Pageable pageable = pageInfo.toPageable();

        Slice<Comment> comments = commentRepository.findAllRepliesByCommentId(UUID.fromString(commentId), pageable);

        log.info("Found {} replies for comment with id: {}", comments.getSize(), commentId);

        return mapToCassandraPage(comments, profileId);
    }

    @Override
    public final CassandraPage<CommentResponse> getCommentsForPost(String postId, String profileId, PageInfo pageInfo) {
        log.info("Getting comments for post with id: {}, with pageable: {}", postId, pageInfo);

        Pageable pageable = pageInfo.toPageable();

        Slice<Comment> comments = commentRepository.findAllByPostId(UUID.fromString(postId), pageable);

        log.info("Found {} comments for post with id: {}", comments.getSize(), postId);

        return mapToCassandraPage(comments, profileId);
    }

    private CassandraPage<CommentResponse> mapToCassandraPage(Slice<Comment> comments, String profileId) {
        List<CommentResponse> data = comments
                .stream()
                .map(comment -> commentMapper.toCommentResponse(comment, profileId))
                .toList();

        CassandraPageRequest nextPageable = (CassandraPageRequest) comments.nextPageable();

        PagingState pagingState = PagingState.newInstance(nextPageable.getPagingState());

        return new CommentsCassandraPage<>(data, nextPageable.getPageSize(), nextPageable.hasNext(), pagingState);
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
    public final CommentResponse editComment(CommentRequest commentRequest) {
        log.info("Editing comment with id: {}, with content: {}", commentRequest.commentKey(),
                commentRequest.content());

        commentValidator.validateCommentRequest(commentRequest);

        CommentKey commentKey = commentMapper.toCommentKey(commentRequest.commentKey());
        Comment comment = getCommentFromRepository(commentKey);

        if (!commentRequest.profileId().equals(comment.getAuthorId())) {
            throw new UserIsNotAuthorException(commentRequest.profileId());
        }
        comment.setContent(commentRequest.content());

        log.info("Saving edited comment with id: {}", comment.getCommentId());

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toCommentResponse(savedComment, commentRequest.profileId());
    }

    @Override
    public final CommentResponse deleteComment(CommentDeleteRequest commentRequest) {
        log.info("Deleting comment with id: {}", commentRequest.commentKey());

        CommentKey commentKey = commentMapper.toCommentKey(commentRequest.commentKey());
        Comment comment = getCommentFromRepository(commentKey);
        String profileId = commentRequest.profileId();

        if (!profileId.equals(comment.getAuthorId())) {
            throw new UserIsNotAuthorException(profileId);
        }
        log.info("Deleting comment...");

        commentRepository.deleteCommentByKey(commentKey);

        return CommentResponse.builder().build();
    }

    private Comment getCommentFromRepository(CommentKey key) {
        Comment comment = commentRepository.findCommentByKey(key)
                .orElseThrow(() -> new CommentDoesNotExistException(key));

        log.info("Found comment with id: {}", comment.getKey());

        return comment;
    }

}
