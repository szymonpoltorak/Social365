package razepl.dev.social365.auth.config.api.auth.devices;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.auth.config.api.auth.devices.data.LoggedDeviceResponse;
import razepl.dev.social365.auth.config.api.auth.devices.interfaces.DeviceController;
import razepl.dev.social365.auth.config.api.auth.devices.interfaces.DeviceService;
import razepl.dev.social365.auth.entities.user.User;

import static razepl.dev.social365.auth.config.api.auth.devices.constants.DeviceMappings.DEVICE_MAPPING;
import static razepl.dev.social365.auth.config.api.auth.devices.constants.DeviceMappings.GET_LOGGED_DEVICES;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = DEVICE_MAPPING)
public class DeviceControllerImpl implements DeviceController {
    private final DeviceService deviceService;

    @Override
    @GetMapping(value = GET_LOGGED_DEVICES)
    public final Page<LoggedDeviceResponse> getLoggedDevicesOnPage(@RequestParam int page,
                                                                   @AuthenticationPrincipal User user) {
        return deviceService.getLoggedDevicesOnPage(page, user);
    }

}
