package securized;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class Server {

    private final static int SERVER_PORT = 4444;

    public static void main(String[] args) {

        // Ask for encryption key file
        System.out.println("# Enter AES encryption file path (aes_key.export) :");
        String aesKeyFilePath = new Scanner(System.in).nextLine();

        try (
            ServerSocket server = new ServerSocket(Server.SERVER_PORT);
        ) {
            AESEncryptor aesEncryptor = new AESEncryptor(false);
            aesEncryptor.importKey(aesKeyFilePath);

            System.out.println("# Server : Successfully started.");

            try (
                Socket client = server.accept();
            ) {
                System.out.println("# Server : Client connected.");

                Thread writerThread = new Thread(new WriterThread(client, aesEncryptor)); writerThread.start();
                Thread readerThread = new Thread(new ReaderThread(client, aesEncryptor)); readerThread.start();

                while (writerThread.isAlive() && readerThread.isAlive());
            }

        } catch (IOException e) {
            System.err.println("# Failed to get Input / Output : " + e.getMessage());
            System.exit(-1);
        } catch (NoSuchAlgorithmException|NoSuchPaddingException e) {
            System.err.println("# Failed to load AES encryptor : "+ e.getMessage());
            System.exit(-1);
        }
    }
}
