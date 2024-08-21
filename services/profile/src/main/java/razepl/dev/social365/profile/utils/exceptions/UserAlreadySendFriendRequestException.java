package razepl.dev.social365.profile.utils.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class UserAlreadySendFriendRequestException extends AbstractException {

    @Serial
    private static final long serialVersionUID = 7528311941512145213L;

    public UserAlreadySendFriendRequestException(String profileId, String friendId) {
        super(HttpStatus.BAD_REQUEST,
                "User with id %s already send friend request to user with id %s".formatted(profileId, friendId));
    }
}
