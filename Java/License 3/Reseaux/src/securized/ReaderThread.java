package securized;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.security.InvalidKeyException;


public class ReaderThread implements Runnable {

    private Socket socket;
    private AESEncryptor aesEncryptor;

    ReaderThread (Socket socket, AESEncryptor aesEncryptor) {
        this.socket = socket;
        this.aesEncryptor = aesEncryptor;
    }

    @Override
    public void run() {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            String received;
            while (null != (received = reader.readLine())) {

                System.out.println("Received : ");
                System.out.println("-- Encrypted : "+ received);
                System.out.println("-- Decrypted : "+ new String(
                    this.aesEncryptor.decrypt(received.getBytes())
                ));
            }

        } catch (IOException e) {
            System.err.println("# Reader : Failed to get Input / Output : "+ e.getMessage());
        } catch (BadPaddingException|IllegalBlockSizeException|InvalidKeyException e) {
            System.err.println("# Reader : Failed to decrypt : "+ e.getMessage());
        }
    }
}
