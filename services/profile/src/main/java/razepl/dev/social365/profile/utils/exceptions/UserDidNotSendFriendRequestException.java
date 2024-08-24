package razepl.dev.social365.profile.utils.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class UserDidNotSendFriendRequestException extends AbstractException {

    @Serial
    private static final long serialVersionUID = -3059051126748047239L;

    public UserDidNotSendFriendRequestException(String profileId, String friendId) {
        super(HttpStatus.BAD_REQUEST,
                "User with id %s did not send friend request to user with id %s".formatted(profileId, friendId));
    }
}
