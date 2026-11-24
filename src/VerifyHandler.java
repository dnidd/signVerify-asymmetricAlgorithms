import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

/**
 * Verify signature using the public key from file 'publicKey'.
 * Update signature using the 'data' file content.
 */
// java VerifyHandler data publicKey signature

public class VerifyHandler {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {

        String data = args[0];
        String publicKey = args[1];
        String signature = args[2];

        FileHandler fileHandler = new FileHandler();

        // Fetch public key from 'publicKey' file.
        PublicKey realPublicKey = fileHandler.fetchPublicKeyFromFile(publicKey);

        // Read signature from file - byte[] returned.
        byte[] signatureBytes = fileHandler.readSignatureFromFile(signature);

        // Verify signature.
        fileHandler.verifySignature(realPublicKey, data, signatureBytes);
    }
}
