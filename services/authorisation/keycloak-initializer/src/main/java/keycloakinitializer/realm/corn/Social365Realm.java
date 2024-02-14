package keycloakinitializer.realm.corn;

import keycloakinitializer.ExternalConfig;
import org.keycloak.representations.idm.RealmRepresentation;

import java.util.List;

public class Social365Realm extends RealmRepresentation {
    private static final String REALM_NAME = "Social365";

    public Social365Realm() {
        setRealm(REALM_NAME);
        setEnabled(true);
        setRegistrationAllowed(true);
        setClients(List.of(new Social365Client()));
        setIdentityProviders(ExternalConfig.getIdentityProviders());
        setPasswordPolicy(buildPasswordPolicy());

        if(ExternalConfig.KCCFG_LOGIN_THEME_NAME != null) {
            setLoginTheme(ExternalConfig.KCCFG_LOGIN_THEME_NAME);
        }
    }

    private String buildPasswordPolicy() {
        String length = "8";
        String digits = "1";
        String specialChars = "1";
        String upperCase = "1";
        String lowerCase = "1";
        String notUsername = "1";

        return String.format("%s and %s and %s and %s and %s and %s",
                length, digits, specialChars, upperCase, lowerCase, notUsername);
    }
}
