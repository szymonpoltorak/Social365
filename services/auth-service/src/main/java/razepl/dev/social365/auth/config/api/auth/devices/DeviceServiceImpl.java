package razepl.dev.social365.auth.config.api.auth.devices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import razepl.dev.social365.auth.config.api.auth.devices.data.LoggedDeviceResponse;
import razepl.dev.social365.auth.config.api.auth.devices.interfaces.DeviceService;
import razepl.dev.social365.auth.entities.devices.LoggedDevice;
import razepl.dev.social365.auth.entities.devices.interfaces.LoggedDeviceMapper;
import razepl.dev.social365.auth.entities.devices.interfaces.LoggedDeviceRepository;
import razepl.dev.social365.auth.entities.user.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private static final int DEVICES_PER_PAGE = 20;
    private final LoggedDeviceRepository loggedDeviceRepository;
    private final LoggedDeviceMapper loggedDeviceMapper;

    @Override
    public final Page<LoggedDeviceResponse> getLoggedDevicesOnPage(int page, User user) {
        Pageable pageable = PageRequest.of(page, DEVICES_PER_PAGE);

        log.info("Getting logged devices on page : {} for user {}", page, user.getUsername());

        Page<LoggedDevice> loggedDevices = loggedDeviceRepository.findAllByUser(user, pageable);

        log.info("Found {} devices", loggedDevices.getTotalElements());

        return loggedDevices.map(loggedDeviceMapper::toLoggedDeviceResponse);
    }

}
