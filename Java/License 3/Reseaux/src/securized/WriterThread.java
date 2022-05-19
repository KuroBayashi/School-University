package securized;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.InvalidKeyException;


public class WriterThread implements Runnable {

    private Socket socket;
    private AESEncryptor aesEncryptor;

    WriterThread(Socket socket, AESEncryptor aesEncryptor) {
        this.socket = socket;
        this.aesEncryptor = aesEncryptor;
    }

    @Override
    public void run() {
        try (
            PrintWriter writer = new PrintWriter(this.socket.getOutputStream(), true);
            BufferedReader prompt = new BufferedReader(new InputStreamReader(System.in));
        ) {
            String sent;
            while (null != (sent = prompt.readLine())) {

                writer.println(new String(this.aesEncryptor.encrypt(sent.getBytes())));
                writer.flush();

                if ("bye".equals(sent)) break;
            }

        } catch (IOException e) {
            System.err.println("# Writer : Failed to get Input / Output : "+ e.getMessage());
        } catch (BadPaddingException|IllegalBlockSizeException|InvalidKeyException e) {
            System.err.println("# Writer : Failed to encrypt : "+ e.getMessage());
        }
    }
}
