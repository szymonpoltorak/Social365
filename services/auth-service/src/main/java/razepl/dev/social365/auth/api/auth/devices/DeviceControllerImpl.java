package razepl.dev.social365.auth.api.auth.devices;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.auth.api.auth.devices.constants.DeviceMappings;
import razepl.dev.social365.auth.api.auth.devices.data.LoggedDeviceResponse;
import razepl.dev.social365.auth.api.auth.devices.interfaces.DeviceController;
import razepl.dev.social365.auth.api.auth.devices.interfaces.DeviceService;
import razepl.dev.social365.auth.entities.user.User;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = DeviceMappings.DEVICE_MAPPING)
public class DeviceControllerImpl implements DeviceController {

    private final DeviceService deviceService;

    @Override
    @GetMapping(value = DeviceMappings.GET_LOGGED_DEVICES)
    public final Page<LoggedDeviceResponse> getLoggedDevicesOnPage(@RequestParam int page,
                                                                   @AuthenticationPrincipal User user) {
        return deviceService.getLoggedDevicesOnPage(page, user);
    }

}
