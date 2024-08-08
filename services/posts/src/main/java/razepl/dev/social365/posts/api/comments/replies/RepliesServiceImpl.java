package razepl.dev.social365.posts.api.comments.replies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.api.comments.replies.data.LikeReplyRequest;
import razepl.dev.social365.posts.api.comments.replies.data.ReplyAddRequest;
import razepl.dev.social365.posts.api.comments.replies.data.ReplyDeleteRequest;
import razepl.dev.social365.posts.api.comments.replies.data.ReplyEditRequest;
import razepl.dev.social365.posts.api.comments.replies.interfaces.RepliesService;
import razepl.dev.social365.posts.clients.images.ImageService;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentMapper;
import razepl.dev.social365.posts.entities.comment.reply.ReplyComment;
import razepl.dev.social365.posts.entities.comment.reply.ReplyCommentKey;
import razepl.dev.social365.posts.entities.comment.reply.data.ReplyKeyResponse;
import razepl.dev.social365.posts.entities.comment.reply.intefaces.ReplyCommentRepository;
import razepl.dev.social365.posts.utils.exceptions.UserIsNotAuthorException;
import razepl.dev.social365.posts.utils.pagination.data.CommentsCassandraPage;
import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepliesServiceImpl implements RepliesService {

    private final ReplyCommentRepository replyCommentRepository;
    private final CommentMapper commentMapper;
    private final ImageService imageService;

    @Override
    public final CassandraPage<CommentResponse> getRepliesForComment(String commentId, String profileId, PageInfo pageInfo) {
        log.info("Getting replies for comment with id: {}, with pageable: {}", commentId, pageInfo);

        Slice<ReplyComment> comments = replyCommentRepository.findAllRepliesByCommentId(UUID.fromString(commentId), pageInfo.toPageable());

        log.info("Found {} replies for comment with id: {}", comments.getSize(), commentId);

        return CommentsCassandraPage.of(comments, replyComment -> commentMapper.toCommentResponse(replyComment, profileId));
    }

    @Override
    public final CommentResponse addReplyToComment(ReplyAddRequest commentRequest) {
        log.info("Adding reply to comment with id: {}, with content: {}", commentRequest.commentId(), commentRequest.content());

        ReplyCommentKey key = ReplyCommentKey
                .builder()
                .replyToCommentId(UUID.fromString(commentRequest.commentId()))
                .creationDateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .replyCommentId(UUID.randomUUID())
                .build();

        ReplyComment replyComment = ReplyComment
                .builder()
                .hasReplies(false)
                .key(key)
                .content(commentRequest.content())
                .authorId(commentRequest.profileId())
                .hasAttachments(commentRequest.hasAttachment())
                .build();

        replyComment = replyCommentRepository.save(replyComment);

        log.info("Added reply : {}", replyComment);

        return commentMapper.toCommentResponse(replyComment, commentRequest.profileId());
    }

    @Override
    public final CommentResponse editReplyComment(ReplyEditRequest commentRequest) {
        log.info("Editing reply with id: {}, with content: {}", commentRequest.replyKey().replyToCommentId(), commentRequest.content());

        ReplyComment replyComment = getReplyComment(commentRequest.replyKey());

        if (!replyComment.isAuthor(commentRequest.profileId())) {
            throw new UserIsNotAuthorException(commentRequest.profileId());
        }
        replyComment.setContent(commentRequest.content());
        replyComment.setHasAttachments(commentRequest.hasAttachment());

        replyComment = replyCommentRepository.save(replyComment);

        log.info("Edited reply : {}", replyComment);

        return commentMapper.toCommentResponse(replyComment, commentRequest.profileId());
    }

    @Override
    public final CommentResponse deleteReplyComment(ReplyDeleteRequest commentRequest) {
        log.info("Deleting reply with id: {}", commentRequest.replyKey().replyToCommentId());

        ReplyComment replyComment = getReplyComment(commentRequest.replyKey());

        if (!replyComment.isAuthor(commentRequest.profileId())) {
            throw new UserIsNotAuthorException(commentRequest.profileId());
        }
        log.info("Deleting reply : {}", replyComment);

        replyCommentRepository.delete(replyComment);

        log.info("Deleting image for comment...");

        imageService.deleteCommentImageById(replyComment.getKey().getReplyCommentId().toString());

        log.info("Deleted reply : {}", replyComment);

        return CommentResponse.builder().build();
    }

    @Override
    public final CommentResponse updateLikeCommentCount(LikeReplyRequest likeCommentRequest) {
        log.info("Updating like count for reply with id: {}", likeCommentRequest.replyKey());

        ReplyComment replyComment = getReplyComment(likeCommentRequest.replyKey());

        if (replyComment.isLikedBy(likeCommentRequest.profileId())) {
            replyComment.getUserLikedIds().remove(likeCommentRequest.profileId());
        } else {
            replyComment.addUserLikedId(likeCommentRequest.profileId());
        }
        ReplyComment savedReplyComment = replyCommentRepository.save(replyComment);

        log.info("Updated like count for reply : {}", savedReplyComment);

        return commentMapper.toCommentResponse(savedReplyComment, likeCommentRequest.profileId());
    }

    private ReplyComment getReplyComment(ReplyKeyResponse keyResponse) {
        ReplyCommentKey key = commentMapper.toCommentReplyKey(keyResponse);

        ReplyComment replyComment = replyCommentRepository.findByKey(key).orElseThrow();

        log.info("Found reply : {}", replyComment);

        return replyComment;
    }

}