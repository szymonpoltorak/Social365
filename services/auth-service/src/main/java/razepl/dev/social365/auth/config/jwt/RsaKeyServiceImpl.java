package razepl.dev.social365.auth.config.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razepl.dev.social365.auth.config.jwt.interfaces.RsaKeyService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RsaKeyServiceImpl implements RsaKeyService {

    private static final String PUBLIC_KEY_PATH = "public.pem";
    private static final String PRIVATE_KEY_PATH = "private.pem";
    private static final String KEY_ALGORITHM = "RSA";

    private Key privateKey = null;
    private Key publicKey = null;

    @Override
    public final Key buildSignInKey() {
        if (privateKey == null) {
            privateKey = buildPrivateKey();
        }
        return privateKey;
    }

    @Override
    public final Key buildVerifyKey() {
        if (publicKey == null) {
            publicKey = buildPublicKey();
        }
        return publicKey;
    }

    private Key buildPublicKey() {
        String publicKeyString = readPublicKey();

        log.error("Encoded key: {}", publicKeyString);

        return deserializePublicKey(publicKeyString);
    }

    private Key deserializePublicKey(String publicKeyString) {
        try {
            byte[] encodedKey = Base64.getDecoder().decode(publicKeyString);

            return KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(encodedKey));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Key buildPrivateKey() {
        String privateKeyString = readPrivateKey();

        return deserializePrivateKey(privateKeyString);
    }

    private String readPublicKey() {
        try (InputStream keyStream = getClass().getClassLoader().getResourceAsStream(PUBLIC_KEY_PATH)) {
            return new Scanner(Objects.requireNonNull(keyStream), StandardCharsets.UTF_8)
                    .useLocale(Locale.UK)
                    .useDelimiter("\n")
                    .tokens()
                    .collect(Collectors.joining("\n"))
                    .replace("-----BEGIN PUBLIC KEY-----\n", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replace("\n", "");
        } catch (IOException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    private String readPrivateKey() {
        try (InputStream keyStream = getClass().getClassLoader().getResourceAsStream(PRIVATE_KEY_PATH)) {
            return new Scanner(Objects.requireNonNull(keyStream), StandardCharsets.UTF_8)
                    .useLocale(Locale.UK)
                    .useDelimiter("\n")
                    .tokens()
                    .collect(Collectors.joining("\n"))
                    .replace("-----BEGIN PRIVATE KEY-----\n", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replace("\n", "");
        } catch (IOException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    private PrivateKey deserializePrivateKey(String serializedPublicKey) {
        try {
            byte[] encodedKey = Base64.getDecoder().decode(serializedPublicKey);

            return KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
