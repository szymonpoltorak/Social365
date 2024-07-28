package razepl.dev.social365.auth.config.jwt.interfaces;

import java.security.Key;

public interface RsaKeyService {

    Key getSignKey();

    Key getVerifyKey();

}
