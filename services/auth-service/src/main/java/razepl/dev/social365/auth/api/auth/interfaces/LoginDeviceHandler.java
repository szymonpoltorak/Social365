package razepl.dev.social365.auth.api.auth.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import razepl.dev.social365.auth.entities.user.User;

@FunctionalInterface
public interface LoginDeviceHandler {

    void addNewDeviceToUserLoggedInDevices(User user, HttpServletRequest request);

}
