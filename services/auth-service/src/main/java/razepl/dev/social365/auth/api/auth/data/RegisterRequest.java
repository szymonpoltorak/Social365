package razepl.dev.social365.auth.api.auth.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import razepl.dev.social365.auth.clients.profile.constants.Params;
import razepl.dev.social365.auth.entities.user.interfaces.Password;

import java.time.LocalDate;

@Builder
public record RegisterRequest(@JsonProperty(Params.FIRST_NAME) String firstName,
                              @JsonProperty(Params.LAST_NAME) String lastName,
                              @JsonProperty(Params.USERNAME) String username,
                              @JsonProperty(Params.PASSWORD) @Password String password,
                              @JsonProperty(Params.DATE_OF_BIRTH) String dateOfBirth) {

    @JsonIgnore
    public LocalDate getDateOfBirth() {
        return LocalDate.parse(dateOfBirth);
    }

}
