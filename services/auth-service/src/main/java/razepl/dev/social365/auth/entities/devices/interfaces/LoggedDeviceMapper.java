package razepl.dev.social365.auth.entities.devices.interfaces;


import razepl.dev.social365.auth.api.auth.devices.data.LoggedDeviceResponse;
import razepl.dev.social365.auth.entities.devices.LoggedDevice;

@FunctionalInterface
public interface LoggedDeviceMapper {

    LoggedDeviceResponse toLoggedDeviceResponse(LoggedDevice loggedDevice);

}
