import java.security.KeyPair;

/**
 * Creates private & public key pair using KeyPairGenerator and KeyPair.
 * Saves keys in separate files: privateKey, publicKey.
 */
// java KeyHandler privateKey publicKey

public class KeyHandler {

    public static void main(String[] args) {
        String privateKey = args[0];
        String publicKey = args[1];

        // Generate KeyPair.
        KeyGenerator keyGenerator = new KeyGenerator();
        KeyPair keyPair = keyGenerator.generatePrivateAndPublicKey();

        FileHandler fileHandler = new FileHandler();

        // Write private key to file.
        fileHandler.writePrivateKeyToFile(keyPair.getPrivate(), privateKey);

        // Write public key to file.
        fileHandler.writePublicKeyToFile(keyPair.getPublic(), publicKey);
    }
}
