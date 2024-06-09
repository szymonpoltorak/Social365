package razepl.dev.social365.posts.api.comments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import razepl.dev.social365.posts.api.comments.data.CommentRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.entities.comment.Comment;
import razepl.dev.social365.posts.entities.comment.CommentKey;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentMapper;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentRepository;
import razepl.dev.social365.posts.utils.exceptions.UserIsNotAuthorException;
import razepl.dev.social365.posts.utils.validators.interfaces.CommentValidator;

import java.time.LocalDateTime;
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

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentValidator commentValidator;

    @BeforeEach
    final void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    final void addCommentToPost_validatesAndSavesComment() {
        UUID postId = UUID.randomUUID();
        CommentRequest commentRequest = new CommentRequest(postId.toString(), "profileId", "content", null, false);
        Comment comment = Comment
                .builder()
                .key(CommentKey
                        .builder()
                        .commentId(UUID.fromString(commentRequest.objectId()))
                        .postId(postId)
                        .build()
                )
                .authorId(commentRequest.profileId())
                .content(commentRequest.content())
                .creationDateTime(LocalDateTime.now())
                .build();
        CommentResponse commentResponse = CommentResponse.builder().build();

        when(commentRepository.save(any(Comment.class)))
                .thenReturn(comment);
        when(commentMapper.toCommentResponse(any(Comment.class), eq(commentRequest.profileId())))
                .thenReturn(commentResponse);

        CommentResponse result = commentService.addCommentToPost(commentRequest);

        verify(commentRepository).save(any(Comment.class));
        assertEquals(commentResponse, result);
    }

    @Test
    final void editComment_validatesAndUpdatesComment() {
        UUID postId = UUID.randomUUID();
        CommentRequest commentRequest = new CommentRequest(postId.toString(), "profileId", "content", null, false);
        Comment comment = Comment
                .builder()
                .key(CommentKey
                        .builder()
                        .commentId(UUID.fromString(commentRequest.objectId()))
                        .postId(postId)
                        .build()
                )
                .authorId(commentRequest.profileId())
                .content("old content")
                .creationDateTime(LocalDateTime.now())
                .build();
        CommentResponse commentResponse = CommentResponse.builder().build();

        when(commentRepository.findById(UUID.fromString(commentRequest.objectId())))
                .thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class)))
                .thenReturn(comment);
        when(commentMapper.toCommentResponse(any(Comment.class), eq(commentRequest.profileId())))
                .thenReturn(commentResponse);

        CommentResponse result = commentService.editComment(commentRequest);

        verify(commentRepository).save(any(Comment.class));
        assertEquals(commentResponse, result);
    }

    @Test
    final void editComment_throwsException_whenUserIsNotAuthor() {
        UUID postId = UUID.randomUUID();
        CommentRequest commentRequest = new CommentRequest(postId.toString(), "profileId", "content", null, false);
        Comment comment = Comment
                .builder()
                .key(CommentKey
                        .builder()
                        .commentId(UUID.fromString(commentRequest.objectId()))
                        .postId(postId)
                        .build()
                )
                .authorId("anotherProfileId")
                .content("old content")
                .creationDateTime(LocalDateTime.now())
                .build();

        when(commentRepository.findById(UUID.fromString(commentRequest.objectId())))
                .thenReturn(Optional.of(comment));

        assertThrows(UserIsNotAuthorException.class, () -> commentService.editComment(commentRequest));
    }

    @Test
    final void deleteComment_deletesComment() {
        UUID postId = UUID.randomUUID();
        String commentId = UUID.randomUUID().toString();
        String profileId = "profileId";
        Comment comment = Comment
                .builder()
                .key(CommentKey
                        .builder()
                        .commentId(UUID.fromString(commentId))
                        .postId(postId)
                        .build()
                )
                .authorId(profileId)
                .content("content")
                .creationDateTime(LocalDateTime.now())
                .build();

        when(commentRepository.findById(UUID.fromString(commentId)))
                .thenReturn(Optional.of(comment));

        commentService.deleteComment(commentId, profileId);

        verify(commentRepository).deleteById(UUID.fromString(commentId));
    }

    @Test
    final void deleteComment_throwsException_whenUserIsNotAuthor() {
        UUID postId = UUID.randomUUID();
        String commentId = UUID.randomUUID().toString();
        String profileId = "profileId";
        Comment comment = Comment
                .builder()
                .key(CommentKey
                        .builder()
                        .commentId(UUID.fromString(commentId))
                        .postId(postId)
                        .build()
                )
                .authorId("anotherProfileId")
                .content("content")
                .creationDateTime(LocalDateTime.now())
                .build();

        when(commentRepository.findById(UUID.fromString(commentId)))
                .thenReturn(Optional.of(comment));

        assertThrows(UserIsNotAuthorException.class, () -> commentService.deleteComment(commentId, profileId));
    }
}
