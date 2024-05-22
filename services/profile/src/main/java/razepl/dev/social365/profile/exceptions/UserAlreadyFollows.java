package razepl.dev.social365.profile.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class UserAlreadyFollows extends AbstractException {

    @Serial
    private static final long serialVersionUID = 3171015623413869374L;

    public UserAlreadyFollows(String profileId, String toFollowId) {
        super(HttpStatus.BAD_REQUEST, "User with id %s already follows user with id %s".formatted(profileId, toFollowId));
    }
}
