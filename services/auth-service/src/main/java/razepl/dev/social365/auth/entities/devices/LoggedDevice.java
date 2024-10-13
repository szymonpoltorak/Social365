package razepl.dev.social365.auth.entities.devices;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import razepl.dev.social365.auth.entities.devices.constants.DeviceType;
import razepl.dev.social365.auth.entities.user.User;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoggedDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long loggedDeviceId;

    private LocalDate dateOfLogin;

    private LocalTime timeOfLogin;

    @Enumerated(value = EnumType.STRING)
    private DeviceType deviceType;

    private String ipAddress;

    @Version
    private long version;

    @ManyToOne
    private User user;

}
