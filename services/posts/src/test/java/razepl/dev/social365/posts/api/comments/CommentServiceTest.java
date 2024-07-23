package razepl.dev.social365.posts.api.comments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import razepl.dev.social365.posts.api.comments.data.CommentAddRequest;
import razepl.dev.social365.posts.api.comments.data.CommentDeleteRequest;
import razepl.dev.social365.posts.api.comments.data.CommentEditRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.entities.comment.Comment;
import razepl.dev.social365.posts.entities.comment.CommentKey;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentMapper;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentRepository;
import razepl.dev.social365.posts.entities.comment.data.CommentKeyResponse;
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
        CommentAddRequest commentRequest = new CommentAddRequest("profileId", "content", false, postId.toString());
        Comment comment = Comment
                .builder()
                .key(CommentKey
                        .builder()
                        .commentId(UUID.randomUUID())
                        .postId(postId)
                        .build()
                )
                .authorId(commentRequest.profileId())
                .content(commentRequest.content())
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
        UUID commentId = UUID.randomUUID();
        CommentKeyResponse commentKey = CommentKeyResponse
                .builder()
                .postId(postId.toString())
                .commentId(commentId.toString())
                .build();
        CommentEditRequest commentEditRequest = new CommentEditRequest(commentKey, "profileId", "content", false);
        Comment comment = Comment
                .builder()
                .key(CommentKey
                        .builder()
                        .commentId(commentId)
                        .postId(postId)
                        .build()
                )
                .authorId(commentEditRequest.profileId())
                .content("old content")
                .build();
        CommentResponse commentResponse = CommentResponse.builder().build();

        when(commentMapper.toCommentKey(commentKey))
                .thenReturn(comment.getKey());
        when(commentRepository.findCommentByKey(comment.getKey()))
                .thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class)))
                .thenReturn(comment);
        when(commentMapper.toCommentResponse(any(Comment.class), eq(commentEditRequest.profileId())))
                .thenReturn(commentResponse);

        CommentResponse result = commentService.editComment(commentEditRequest);

        assertEquals(commentResponse, result);

        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    final void editComment_throwsException_whenUserIsNotAuthor() {
        UUID postId = UUID.randomUUID();
        UUID commentId = UUID.randomUUID();
        CommentKeyResponse commentKey = CommentKeyResponse
                .builder()
                .postId(postId.toString())
                .commentId(commentId.toString())
                .build();
        CommentEditRequest commentEditRequest = new CommentEditRequest(commentKey, "profileId", "content", false);
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

        assertThrows(UserIsNotAuthorException.class, () -> commentService.editComment(commentEditRequest));
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
        CommentDeleteRequest commentRequest = new CommentDeleteRequest(commentKeyResponse, profileId);
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

        commentService.deleteComment(commentRequest);

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
        CommentDeleteRequest commentRequest = new CommentDeleteRequest(commentKeyResponse, profileId);
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

        assertThrows(UserIsNotAuthorException.class, () -> commentService.deleteComment(commentRequest));
    }
}
