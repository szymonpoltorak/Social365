package razepl.dev.social365.auth.entities.devices.constants;

public enum DeviceType {
    DESKTOP,
    MOBILE;

    public static DeviceType getDeviceType(String userAgent) {
        if (userAgent.contains("Android") || userAgent.contains("iPhone")) {
            return MOBILE;
        }
        return DESKTOP;
    }
}
