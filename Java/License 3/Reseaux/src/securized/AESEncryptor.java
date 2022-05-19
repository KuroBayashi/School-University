package securized;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESEncryptor {

    private final static String ALGORITHM = "AES";

    private Key key;
    private Cipher cipher;

    // Constructors
    private AESEncryptor() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this(true);
    }

    AESEncryptor(Boolean generateKey) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance(AESEncryptor.ALGORITHM);

        if (generateKey) this.generateKey();
    }

    // Private Methods
    private void generateKey() throws NoSuchAlgorithmException {
        this.key = KeyGenerator.getInstance(AESEncryptor.ALGORITHM).generateKey();
    }

    // Public Methods
    void importKey(String filePath) throws IOException {
        byte[] data = new FileInputStream(filePath).readAllBytes();

        this.key = new SecretKeySpec(
                data,
                0,
                data.length,
                AESEncryptor.ALGORITHM
        );
    }

    private void exportKey(String filePath) throws IOException {
        FileOutputStream fileStream = new FileOutputStream(filePath);

        fileStream.write(this.key.getEncoded());
        fileStream.close();
    }

    byte[] encrypt(byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(Cipher.ENCRYPT_MODE, this.key);

        return Base64.getEncoder().encode(this.cipher.doFinal(data));
    }

    byte[] decrypt(byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(Cipher.DECRYPT_MODE, this.key);

        return this.cipher.doFinal(Base64.getDecoder().decode(data));
    }

    // Test
    public static void main(String[] args) {
        try {
            AESEncryptor aes = new AESEncryptor();

            String message = "Hello World!";
            byte[] data;

            System.out.println(message);

            aes.exportKey("aes_key.export");
            System.out.println("AES key exported.");

            aes = new AESEncryptor(false);
            aes.importKey("aes_key.export");
            System.out.println("AES key imported.");

            data = aes.encrypt(message.getBytes());
            System.out.println(new String(data));

            data = aes.decrypt(new String(data).getBytes());
            System.out.println(new String(data));


        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
            e.printStackTrace();
        }

    }
}

