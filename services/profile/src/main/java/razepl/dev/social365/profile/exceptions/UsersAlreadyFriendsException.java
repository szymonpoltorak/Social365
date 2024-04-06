package razepl.dev.social365.profile.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class UsersAlreadyFriendsException extends AbstractException {

    @Serial
    private static final long serialVersionUID = -4546231958866352256L;

    public UsersAlreadyFriendsException(String profileId, String friendId) {
        super(HttpStatus.BAD_REQUEST,
                String.format("Users with profileId: %s and friendId: %s are already friends", profileId, friendId));
    }
}
