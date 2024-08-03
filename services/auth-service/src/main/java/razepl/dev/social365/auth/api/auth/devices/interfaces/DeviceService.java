package razepl.dev.social365.auth.api.auth.devices.interfaces;


import org.springframework.data.domain.Page;
import razepl.dev.social365.auth.api.auth.devices.data.LoggedDeviceResponse;
import razepl.dev.social365.auth.entities.user.User;

@FunctionalInterface
public interface DeviceService {

    Page<LoggedDeviceResponse> getLoggedDevicesOnPage(int page, User user);

}
