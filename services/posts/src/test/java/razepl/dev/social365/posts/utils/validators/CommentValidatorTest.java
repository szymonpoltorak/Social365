package razepl.dev.social365.posts.utils.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import razepl.dev.social365.posts.api.comments.data.CommentEditRequest;
import razepl.dev.social365.posts.entities.comment.data.CommentKeyResponse;
import razepl.dev.social365.posts.utils.exceptions.InvalidCommentRequestException;
import razepl.dev.social365.posts.utils.validators.interfaces.CommentValidator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommentValidatorTest {

    private CommentValidator commentValidator;

    @BeforeEach
    final void setUp() {
        commentValidator = new CommentValidatorImpl();
    }

    @Test
    final void validateCommentRequest_throwsException_whenContentIsNull() {
        // given
        CommentEditRequest commentEditRequest = new CommentEditRequest(CommentKeyResponse.builder().build(), null, false);

        // when

        // then
        assertThrows(InvalidCommentRequestException.class, () -> commentValidator.validateCommentRequest(commentEditRequest, "profileId"));
    }

    @Test
    final void validateCommentRequest_throwsException_whenContentIsEmpty() {
        // given
        CommentEditRequest commentEditRequest = new CommentEditRequest(CommentKeyResponse.builder().build(), "", false);

        // when

        // then
        assertThrows(InvalidCommentRequestException.class, () -> commentValidator.validateCommentRequest(commentEditRequest, "profileId"));
    }

    @Test
    final void validateCommentRequest_throwsException_whenContentIsBlank() {
        // given
        CommentEditRequest commentEditRequest = new CommentEditRequest(CommentKeyResponse.builder().build(), "   ", false);

        // when

        // then
        assertThrows(InvalidCommentRequestException.class, () -> commentValidator.validateCommentRequest(commentEditRequest, "profileId"));
    }

    @Test
    final void validateCommentRequest_throwsException_whenContentIsTooLong() {
        // given
        String longContent = "a".repeat(501);
        CommentEditRequest commentEditRequest = new CommentEditRequest(CommentKeyResponse.builder().build(),  longContent, false);

        // when

        // then
        assertThrows(InvalidCommentRequestException.class, () -> commentValidator.validateCommentRequest(commentEditRequest, "profileId"));
    }

    @Test
    final void validateCommentRequest_throwsException_whenObjectIdIsNull() {
        // given
        CommentEditRequest commentEditRequest = new CommentEditRequest(CommentKeyResponse.builder().build(), "content", false);

        // when

        // then
        assertThrows(InvalidCommentRequestException.class, () -> commentValidator.validateCommentRequest(commentEditRequest, null));
    }

    @Test
    final void validateCommentRequest_throwsException_whenProfileIdIsNull() {
        // given
        CommentEditRequest commentEditRequest = new CommentEditRequest(CommentKeyResponse.builder().build(), "content", false);

        // when

        // then
        assertThrows(InvalidCommentRequestException.class, () -> commentValidator.validateCommentRequest(commentEditRequest, null));
    }

    @Test
    final void validateCommentRequest_doesNotThrowException_whenRequestIsValid() {
        // given
        CommentKeyResponse response = CommentKeyResponse.builder().postId("postId").build();
        CommentEditRequest commentEditRequest = new CommentEditRequest(response, "content", false);

        // when

        // then
        assertDoesNotThrow(() -> commentValidator.validateCommentRequest(commentEditRequest, "profileId"));
    }
}
