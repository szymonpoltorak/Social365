package razepl.dev.social365.profile.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class UsersAreNotFriendsException extends AbstractException {

    @Serial
    private static final long serialVersionUID = 7986027684475733378L;

    public UsersAreNotFriendsException(String profileId, String friendId) {
        super(HttpStatus.BAD_REQUEST,
                String.format("Users with profileId: %s and friendId: %s are not friends", profileId, friendId));
    }
}
