package keycloakinitializer.realm.social365;

import org.keycloak.representations.idm.ClientRepresentation;

import java.util.List;

final class Social365Client extends ClientRepresentation {
    private static final String CLIENT_ID = "Social365";
    private static final String OPENID_CONNECT = "openid-connect";

    Social365Client() {
        setClientId(CLIENT_ID);
        setEnabled(true);
        setRedirectUris(List.of(
                "http://localhost/*",
                "http://localhost:4200/*",
                "http://localhost:80/*"
        ));
        setDirectAccessGrantsEnabled(true);
        setPublicClient(true);
        setProtocol(OPENID_CONNECT);
        setFullScopeAllowed(true);
    }
}
