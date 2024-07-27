package razepl.dev.social365.auth.config.api.auth.devices.interfaces;


import org.springframework.data.domain.Page;
import razepl.dev.social365.auth.config.api.auth.devices.data.LoggedDeviceResponse;
import razepl.dev.social365.auth.entities.user.User;

@FunctionalInterface
public interface DeviceController {

    Page<LoggedDeviceResponse> getLoggedDevicesOnPage(int page, User user);

}
