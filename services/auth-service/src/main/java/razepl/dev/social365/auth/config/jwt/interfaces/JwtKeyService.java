package razepl.dev.social365.auth.config.jwt.interfaces;

import com.nimbusds.jose.jwk.JWKSet;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface JwtKeyService {

    PrivateKey getSignKey();

    PublicKey getVerifyKey();

    JWKSet getJwkSet();
}
