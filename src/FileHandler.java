import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * Write/read keys, data, signature to and from files.
 * Verify signature with data and public key.
 */
public class FileHandler {

    /**
     * Write private key to file.
     * @param privateKey = PrivateKey.
     * @param filename = file to save private key.
     */
    public void writePrivateKeyToFile(PrivateKey privateKey, String filename) {
        byte[] encodedKey = privateKey.getEncoded();

        try (FileOutputStream out = new FileOutputStream(filename)) {
            out.write(encodedKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Write public key to file.
     * @param publicKey = PublicKey.
     * @param filename = file to save public key.
     */
    public void writePublicKeyToFile(PublicKey publicKey, String filename) {
        byte[] encodedKey = publicKey.getEncoded();

        try (FileOutputStream out = new FileOutputStream(filename)) {
            out.write(encodedKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Fetch private key from file.
     * @param privateKeyFile = file storing private key.
     * @return PrivateKey.
     * @throws IOException e.
     * @throws NoSuchAlgorithmException e.
     * @throws InvalidKeySpecException e.
     */
    public PrivateKey fetchPrivateKeyFromFile(String privateKeyFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[]  encodedPrivateKey = Files.readAllBytes(Path.of(privateKeyFile));
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedPrivateKey));
        return privateKey;
    }


    /**
     * Fetch public key from file.
     * @param publicKeyFile = file storing public key.
     * @return PublicKey.
     * @throws IOException e.
     * @throws NoSuchAlgorithmException e.
     * @throws InvalidKeySpecException e.
     */
    public PublicKey fetchPublicKeyFromFile(String publicKeyFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encodedPublicKey = Files.readAllBytes(Path.of(publicKeyFile));
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        PublicKey publicKey = keyFactory.generatePublic( new X509EncodedKeySpec(encodedPublicKey));
        return publicKey;
    }


    /**
     * Generate signature with private key.
     * @param privateKey = PrivateKey.
     * @return Signature.
     */
    public Signature generatePrivateKeySignature(PrivateKey privateKey) {

        try {
            // Initialize Signature object with private key.
            Signature signature = Signature.getInstance("SHA256withDSA");
            signature.initSign(privateKey);

            return signature;

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Create message digest.
     * Sign data in file 'data'.
     * @param data = file of data.
     * @param signature = Signature with private key.
     * @return signed data.
     * @throws SignatureException e.
     */
    public byte[] signData(String data, Signature signature) throws SignatureException {

        // Read data to be signed.
        try (BufferedInputStream buffIn = new BufferedInputStream( new FileInputStream(data) )) {
            byte[] buffer = new byte[1024];
            int len;
            // Sign data.
            while( (len = buffIn.read(buffer)) >= 0 ) {
                signature.update(buffer, 0, len);
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] sign = signature.sign();

        return sign;
    }


    /**
     * Write signature/signed data to file 'signature'.
     * @param signature = file to be created.
     * @param signatureData = content data to be written to file.
     */
    public void writeSignatureToFile(String signature, byte[] signatureData) {
        try (FileOutputStream out = new FileOutputStream(signature)) {
            out.write(signatureData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Read signature from file.
     * @param signature = file containing signature.
     * @return signature byte[].
     */
    public byte[] readSignatureFromFile(String signature) {
        byte[] signatureBytes;
        try (FileInputStream instream = new FileInputStream(signature)) {
            signatureBytes = instream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return signatureBytes;
    }


    /**
     * Create message digest.
     * Verifies signature using public key.
     * Prints boolean value depending on if verify or not.
     * @param publicKey = PublicKey
     * @param data = file containing signed data.
     * @param signatureBytes = signature in byte[].
     * @throws InvalidKeyException e.
     * @throws NoSuchAlgorithmException e.
     * @throws IOException e.
     * @throws SignatureException e.
     */
    public void verifySignature( PublicKey publicKey, String data, byte[] signatureBytes) throws InvalidKeyException, NoSuchAlgorithmException, IOException, SignatureException {

        // Initialize Signature object with public key.
        Signature signature = Signature.getInstance("SHA256withDSA");
        signature.initVerify(publicKey);

        // Read file containing data.
        byte[] dataBytes;
        try (FileInputStream instream = new FileInputStream(data)){
            dataBytes = instream.readAllBytes();
        }

        // Supply the Signature Object With the Data to be Verified.
        signature.update(dataBytes);

        // Verity the signature.
        boolean verifies = signature.verify(signatureBytes);

        System.out.println("Verifies: " + verifies);
    }

}
