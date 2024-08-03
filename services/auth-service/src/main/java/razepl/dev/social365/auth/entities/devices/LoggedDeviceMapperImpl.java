package razepl.dev.social365.auth.entities.devices;

import org.springframework.stereotype.Component;
import razepl.dev.social365.auth.api.auth.devices.data.LoggedDeviceResponse;
import razepl.dev.social365.auth.entities.devices.interfaces.LoggedDeviceMapper;

import java.time.format.DateTimeFormatter;

@Component
public class LoggedDeviceMapperImpl implements LoggedDeviceMapper {

    @Override
    public final LoggedDeviceResponse toLoggedDeviceResponse(LoggedDevice loggedDevice) {
        return LoggedDeviceResponse
                .builder()
                .deviceType(loggedDevice.getDeviceType().name())
                .dateOfLogin(loggedDevice.getDateOfLogin())
                .ipAddress(loggedDevice.getIpAddress())
                .timeOfLogin(loggedDevice.getTimeOfLogin().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

}
