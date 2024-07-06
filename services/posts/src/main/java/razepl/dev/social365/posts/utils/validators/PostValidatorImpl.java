package razepl.dev.social365.posts.utils.validators;

import org.springframework.stereotype.Component;
import razepl.dev.social365.posts.utils.exceptions.InvalidPostContentException;
import razepl.dev.social365.posts.utils.validators.interfaces.PostValidator;

@Component
public class PostValidatorImpl implements PostValidator {

    private static final int MAX_POST_CONTENT_LENGTH = 1000;

    @Override
    public final void validatePostContent(String content) {
        if (content == null) {
            return;
        }
        int contentLength = content.length();

        if (contentLength > MAX_POST_CONTENT_LENGTH) {
            throw new InvalidPostContentException("Post content needs to be shorter than 1000 characters!");
        }
    }

}
