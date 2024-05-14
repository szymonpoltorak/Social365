package razepl.dev.social365.posts.utils.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import razepl.dev.social365.posts.api.comments.data.CommentRequest;
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
        CommentRequest commentRequest = new CommentRequest(null, "objectId", "profileId");

        // when

        // then
        assertThrows(InvalidCommentRequestException.class, () -> commentValidator.validateCommentRequest(commentRequest));
    }

    @Test
    final void validateCommentRequest_throwsException_whenContentIsEmpty() {
        // given
        CommentRequest commentRequest = new CommentRequest("", "objectId", "profileId");

        // when

        // then
        assertThrows(InvalidCommentRequestException.class, () -> commentValidator.validateCommentRequest(commentRequest));
    }

    @Test
    final void validateCommentRequest_throwsException_whenContentIsBlank() {
        // given
        CommentRequest commentRequest = new CommentRequest("   ", "objectId", "profileId");

        // when

        // then
        assertThrows(InvalidCommentRequestException.class, () -> commentValidator.validateCommentRequest(commentRequest));
    }

    @Test
    final void validateCommentRequest_throwsException_whenContentIsTooShort() {
        // given
        CommentRequest commentRequest = new CommentRequest("objectId", "profileId", "a");

        // when

        // then
        assertThrows(InvalidCommentRequestException.class, () -> commentValidator.validateCommentRequest(commentRequest));
    }

    @Test
    final void validateCommentRequest_throwsException_whenContentIsTooLong() {
        // given
        String longContent = "a".repeat(501);
        CommentRequest commentRequest = new CommentRequest("objectId", "profileId", longContent);

        // when

        // then
        assertThrows(InvalidCommentRequestException.class, () -> commentValidator.validateCommentRequest(commentRequest));
    }

    @Test
    final void validateCommentRequest_throwsException_whenObjectIdIsNull() {
        // given
        CommentRequest commentRequest = new CommentRequest("Valid content", null, "profileId");

        // when

        // then
        assertThrows(InvalidCommentRequestException.class, () -> commentValidator.validateCommentRequest(commentRequest));
    }

    @Test
    final void validateCommentRequest_throwsException_whenProfileIdIsNull() {
        // given
        CommentRequest commentRequest = new CommentRequest("Valid content", "objectId", null);

        // when

        // then
        assertThrows(InvalidCommentRequestException.class, () -> commentValidator.validateCommentRequest(commentRequest));
    }

    @Test
    final void validateCommentRequest_doesNotThrowException_whenRequestIsValid() {
        // given
        CommentRequest commentRequest = new CommentRequest("Valid content", "objectId", "profileId");

        // when

        // then
        assertDoesNotThrow(() -> commentValidator.validateCommentRequest(commentRequest));
    }
}
