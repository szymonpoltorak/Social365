package razepl.dev.social365.auth.clients.profile.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import razepl.dev.social365.auth.clients.profile.constants.Params;
import razepl.dev.social365.auth.entities.user.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
public record ProfileRequest(@JsonProperty(Params.USER_ID) long userId,
                             @JsonProperty(Params.USERNAME) String username,
                             @JsonProperty(Params.FIRST_NAME) String firstName,
                             @JsonProperty(Params.LAST_NAME) String lastName,
                             @JsonProperty(Params.DATE_OF_BIRTH) String dateOfBirth) {

    public static ProfileRequest of(User user, LocalDate dateOfBirth) {
        return ProfileRequest.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(dateOfBirth.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .build();
    }

}
