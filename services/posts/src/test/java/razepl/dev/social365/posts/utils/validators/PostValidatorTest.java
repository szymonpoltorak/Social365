package razepl.dev.social365.posts.utils.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import razepl.dev.social365.posts.utils.exceptions.InvalidPostContentException;
import razepl.dev.social365.posts.utils.validators.interfaces.PostValidator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostValidatorTest {

    private PostValidator postValidator;

    @BeforeEach
    final void setUp() {
        postValidator = new PostValidatorImpl();
    }

    @Test
    final void validatePostContent_throwsException_whenContentIsNull() {
        // given

        // when

        // then
        assertThrows(InvalidPostContentException.class, () -> postValidator.validatePostContent(null));
    }

    @Test
    final void validatePostContent_throwsException_whenContentIsEmpty() {
        // given

        // when

        // then
        assertThrows(InvalidPostContentException.class, () -> postValidator.validatePostContent(""));
    }

    @Test
    final void validatePostContent_throwsException_whenContentIsBlank() {
        // given

        // when

        // then
        assertThrows(InvalidPostContentException.class, () -> postValidator.validatePostContent("   "));
    }

    @Test
    final void validatePostContent_throwsException_whenContentIsTooShort() {
        // given

        // when

        // then
        assertThrows(InvalidPostContentException.class, () -> postValidator.validatePostContent("ab"));
    }

    @Test
    final void validatePostContent_throwsException_whenContentIsTooLong() {
        // given
        String longContent = "a".repeat(1001);

        // when

        // then
        assertThrows(InvalidPostContentException.class, () -> postValidator.validatePostContent(longContent));
    }

    @Test
    final void validatePostContent_doesNotThrowException_whenContentIsJustRight() {
        // given
        String validContent = "This is a valid post content.";

        // when

        // then
        assertDoesNotThrow(() -> postValidator.validatePostContent(validContent));
    }

}
