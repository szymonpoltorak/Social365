package razepl.dev.social365.posts.utils.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class PostDoesNotExistException extends AbstractException {

    @Serial
    private static final long serialVersionUID = 6252810432666949715L;

    public PostDoesNotExistException(String postId, String authorId) {
        super(HttpStatus.NOT_FOUND, "Post of id %s from author %s does not exist".formatted(postId, authorId));
    }

}
