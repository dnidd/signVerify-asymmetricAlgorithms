import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

/**
 * Generate signature using private key.
 * Sign file 'data'.
 * Writes signature to file 'signature'.
 */
// java SignHandler data privateKey signature

public class SignHandler {

    public static void main (String[] args) throws SignatureException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {

        String data = args[0];             // file of data.
        String privateKeyFile = args[1];   // private key.
        String signature = args[2];        // signatur saved to file signature.

        FileHandler fileHandler = new FileHandler();

        // Read private key from fil. Encoded key returned.
        PrivateKey privateKey = fileHandler.fetchPrivateKeyFromFile(privateKeyFile);

        // Generate signature.
        Signature dsa = fileHandler.generatePrivateKeySignature(privateKey);

        // sign data in file 'data'.
        byte[] signatureBytes = fileHandler.signData(data, dsa);

        // Write signature to file 'signature'.
        fileHandler.writeSignatureToFile(signature, signatureBytes);
    }
}
