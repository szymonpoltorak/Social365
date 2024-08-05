package razepl.dev.social365.auth.config.jwt.interfaces;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface JwtKeyService {

    PrivateKey getSignKey();

    PublicKey getVerifyKey();

    JWKSource<SecurityContext> getJwkSource();

}
