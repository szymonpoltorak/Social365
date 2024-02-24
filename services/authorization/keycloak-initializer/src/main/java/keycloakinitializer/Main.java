package keycloakinitializer;

import keycloakinitializer.realm.social365.Social365Realm;
import org.keycloak.admin.client.Keycloak;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Keycloak admin = ExternalConfig.getAdmin();
        Social365Realm realm = new Social365Realm();

        admin
                .realms()
                .findAll()
                .stream()
                .filter(r -> r.getRealm().equals(realm.getRealm()))
                .findFirst()
                .ifPresentOrElse(
                        r -> {
                            if (!ExternalConfig.shouldOverrideExistingConfiguration()) {
                                throw new UnsupportedOperationException("Configured not to override existing configuration => Exiting");
                            }
                            admin.realms().realm(r.getRealm()).remove();
                        },
                        () -> admin.realms().create(realm)
                );
    }
}