package razepl.dev.social365.posts.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class UserIsNotAuthorException extends AbstractException {

    @Serial
    private static final long serialVersionUID = 3474933945377402654L;

    public UserIsNotAuthorException(String profileId) {
        super(HttpStatus.UNAUTHORIZED, "User of id %s is not the author of this post".formatted(profileId));
    }

}
