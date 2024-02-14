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

        if(ExternalConfig.KCCFG_LOGIN_THEME_NAME != null) {
            setLoginTheme(ExternalConfig.KCCFG_LOGIN_THEME_NAME);
        }
    }
}
