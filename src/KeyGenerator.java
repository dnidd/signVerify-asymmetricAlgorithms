import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * Generate KeyPair containing private and public keys.
 */
public class KeyGenerator {
    KeyPair pair;

    /**
     * Generate KeyPair.
     * @return KeyPair
     */
    public KeyPair generatePrivateAndPublicKey() {

        // Get a key-pair generator object, generating keys for the DSA signature algorithm.
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
            // Initialize the key pair generator using key size and a default source of randomness.
            keyGen.initialize(1024);

            pair = keyGen.generateKeyPair();
            return pair;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
