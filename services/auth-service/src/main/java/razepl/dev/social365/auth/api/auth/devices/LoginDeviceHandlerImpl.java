package razepl.dev.social365.auth.api.auth.devices;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import razepl.dev.social365.auth.api.auth.interfaces.LoginDeviceHandler;
import razepl.dev.social365.auth.entities.devices.LoggedDevice;
import razepl.dev.social365.auth.entities.devices.constants.DeviceType;
import razepl.dev.social365.auth.entities.devices.interfaces.LoggedDeviceRepository;
import razepl.dev.social365.auth.entities.user.User;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginDeviceHandlerImpl implements LoginDeviceHandler {
    private final LoggedDeviceRepository loggedDeviceRepository;

    @Override
    public final void addNewDeviceToUserLoggedInDevices(User user, HttpServletRequest request) {
        String remoteAddress = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        DeviceType deviceType = DeviceType.getDeviceType(userAgent);

        log.info("Adding new device to user: {} with ip: {}", user, remoteAddress);
        log.info("Login device type: {}", deviceType);

        LoggedDevice loggedDevice = LoggedDevice
                .builder()
                .ipAddress(remoteAddress)
                .deviceType(deviceType)
                .dateOfLogin(LocalDate.now())
                .timeOfLogin(LocalTime.now())
                .user(user)
                .build();
        log.info("Logged device: {}", loggedDevice);

        loggedDeviceRepository.save(loggedDevice);
    }

    private String getClientIp(HttpServletRequest request) {
        String[] headers = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_VIA",
                "REMOTE_ADDR"
        };
        String unknownIp = "unknown";

        for (String header : headers) {
            String ip = request.getHeader(header);

            if (StringUtils.hasLength(ip) && !unknownIp.equalsIgnoreCase(ip)) {
                return ip.split(",")[0];
            }
        }
        return request.getRemoteAddr();
    }

}
