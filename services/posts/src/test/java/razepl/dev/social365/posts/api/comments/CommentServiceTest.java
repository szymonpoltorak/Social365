package razepl.dev.social365.posts.api.comments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import razepl.dev.social365.posts.api.comments.data.CommentAddRequest;
import razepl.dev.social365.posts.api.comments.data.CommentEditRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.clients.images.ImageService;
import razepl.dev.social365.posts.config.User;
import razepl.dev.social365.posts.entities.comment.Comment;
import razepl.dev.social365.posts.entities.comment.CommentKey;
import razepl.dev.social365.posts.entities.comment.data.CommentKeyResponse;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentMapper;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentRepository;
import razepl.dev.social365.posts.entities.comment.reply.intefaces.ReplyCommentRepository;
import razepl.dev.social365.posts.utils.exceptions.UserIsNotAuthorException;
import razepl.dev.social365.posts.utils.validators.interfaces.CommentValidator;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private ReplyCommentRepository replyCommentRepository;

    @Mock
    private CommentValidator commentValidator;

    @BeforeEach
    final void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    final void addCommentToPost_validatesAndSavesComment() {
        UUID postId = UUID.randomUUID();
        String profileId = "profileId";
        User user = User.builder().profileId(profileId).build();
        CommentAddRequest commentRequest = new CommentAddRequest("content", false, postId.toString());
        Comment comment = Comment
                .builder()
                .key(CommentKey
                        .builder()
                        .commentId(UUID.randomUUID())
                        .postId(postId)
                        .build()
                )
                .authorId(user.profileId())
                .content(commentRequest.content())
                .build();
        CommentResponse commentResponse = CommentResponse.builder().build();

        when(commentRepository.save(any(Comment.class)))
                .thenReturn(comment);
        when(commentMapper.toCommentResponseNoImage(any(Comment.class), eq(user.profileId())))
                .thenReturn(commentResponse);

        CommentResponse result = commentService.addCommentToPost(user, commentRequest);

        verify(commentRepository).save(any(Comment.class));
        assertEquals(commentResponse, result);
    }

    @Test
    final void editComment_validatesAndUpdatesComment() {
        UUID postId = UUID.randomUUID();
        UUID commentId = UUID.randomUUID();
        String profileId = "profileId";
        CommentKeyResponse commentKey = CommentKeyResponse
                .builder()
                .postId(postId.toString())
                .commentId(commentId.toString())
                .build();
        User user = User.builder().profileId(profileId).build();
        CommentEditRequest commentEditRequest = new CommentEditRequest(commentKey, "content", false);
        Comment comment = Comment
                .builder()
                .key(CommentKey
                        .builder()
                        .commentId(commentId)
                        .postId(postId)
                        .build()
                )
                .authorId(profileId)
                .content("old content")
                .build();
        CommentResponse commentResponse = CommentResponse.builder().build();

        when(commentMapper.toCommentKey(commentKey))
                .thenReturn(comment.getKey());
        when(commentRepository.findCommentByKey(comment.getKey()))
                .thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class)))
                .thenReturn(comment);
        when(commentMapper.toCommentResponse(any(Comment.class), eq(profileId)))
                .thenReturn(commentResponse);

        CommentResponse result = commentService.editComment(user, commentEditRequest);

        assertEquals(commentResponse, result);

        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    final void editComment_throwsException_whenUserIsNotAuthor() {
        String profileId = "profileId";
        UUID postId = UUID.randomUUID();
        UUID commentId = UUID.randomUUID();
        CommentKeyResponse commentKey = CommentKeyResponse
                .builder()
                .postId(postId.toString())
                .commentId(commentId.toString())
                .build();
        CommentEditRequest commentEditRequest = new CommentEditRequest(commentKey, "content", false);
        User user = User.builder().profileId(profileId).build();
        Comment comment = Comment
                .builder()
                .key(CommentKey
                        .builder()
                        .commentId(commentId)
                        .postId(postId)
                        .build()
                )
                .authorId("anotherProfileId")
                .content("old content")
                .build();

        when(commentMapper.toCommentKey(commentKey))
                .thenReturn(comment.getKey());
        when(commentRepository.findCommentByKey(comment.getKey()))
                .thenReturn(Optional.of(comment));

        assertThrows(UserIsNotAuthorException.class, () -> commentService.editComment(user, commentEditRequest));
    }

    @Test
    final void deleteComment_deletesComment() {
        UUID postId = UUID.randomUUID();
        UUID commentId = UUID.randomUUID();
        String profileId = "profileId";
        CommentKeyResponse commentKeyResponse = CommentKeyResponse
                .builder()
                .commentId(commentId.toString())
                .postId(postId.toString())
                .creationDateTime("2021-01-01T00:00:00")
                .build();
        User user = User.builder().profileId(profileId).build();
        Comment comment = Comment
                .builder()
                .key(CommentKey
                        .builder()
                        .commentId(commentId)
                        .postId(postId)
                        .creationDateTime(commentKeyResponse.creationDateTime())
                        .build()
                )
                .authorId(profileId)
                .content("content")
                .build();

        when(commentMapper.toCommentKey(commentKeyResponse))
                .thenReturn(comment.getKey());
        when(commentRepository.findCommentByKey(comment.getKey()))
                .thenReturn(Optional.of(comment));

        commentService.deleteComment(user, commentKeyResponse);

        verify(commentRepository).deleteCommentByKey(comment.getKey());
    }

    @Test
    final void deleteComment_throwsException_whenUserIsNotAuthor() {
        UUID postId = UUID.randomUUID();
        UUID commentId = UUID.randomUUID();
        String profileId = "profileId";
        CommentKeyResponse commentKeyResponse = CommentKeyResponse
                .builder()
                .commentId(commentId.toString())
                .postId(postId.toString())
                .creationDateTime("2021-01-01T00:00:00")
                .build();
        User user = User.builder().profileId(profileId).build();
        Comment comment = Comment
                .builder()
                .key(CommentKey
                        .builder()
                        .commentId(commentId)
                        .postId(postId)
                        .build()
                )
                .authorId("anotherProfileId")
                .content("content")
                .build();

        when(commentMapper.toCommentKey(commentKeyResponse))
                .thenReturn(comment.getKey());
        when(commentRepository.findCommentByKey(comment.getKey()))
                .thenReturn(Optional.of(comment));

        assertThrows(UserIsNotAuthorException.class, () -> commentService.deleteComment(user, commentKeyResponse));
    }
}
