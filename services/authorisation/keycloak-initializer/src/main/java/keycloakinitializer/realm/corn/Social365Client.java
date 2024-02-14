package keycloakinitializer.realm.corn;

import org.keycloak.representations.idm.ClientRepresentation;

import java.util.List;

public class Social365Client extends ClientRepresentation {
    private static final String CLIENT_ID = "Social365";
    private static final String PROTOCOL = "openid-connect";

    public Social365Client() {
        setClientId(CLIENT_ID);
        setEnabled(true);
        setRedirectUris(List.of(
                "http://localhost/*",
                "http://localhost:4200/*",
                "http://localhost:80/*"
        ));
        setDirectAccessGrantsEnabled(true);
        setPublicClient(true);
        setProtocol(PROTOCOL);
        setFullScopeAllowed(true);
    }

}
