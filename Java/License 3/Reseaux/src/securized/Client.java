package securized;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class Client {

    public static void main(String[] args) {

        // Ask for server information
        System.out.println("# Enter server address (localhost) :");
        String address = new Scanner(System.in).nextLine();

        System.out.println("# Enter server port (4444) :");
        int port = new Scanner(System.in).nextInt();

        // Ask for encryption key file
        System.out.println("# Enter AES encryption file path (aes_key.export) :");
        String aesKeyFilePath = new Scanner(System.in).nextLine();

        try (
            Socket socket = new Socket(InetAddress.getByName(address), port);
        ) {
            AESEncryptor aesEncryptor = new AESEncryptor(false);
            aesEncryptor.importKey(aesKeyFilePath);

            System.out.println("# Client : Successfully connected.");

            Thread writerThread = new Thread(new WriterThread(socket, aesEncryptor)); writerThread.start();
            Thread readerThread = new Thread(new ReaderThread(socket, aesEncryptor)); readerThread.start();

            while (writerThread.isAlive() && readerThread.isAlive());

        } catch (UnknownHostException e) {
            System.err.println("# Unknown host : "+ e.getMessage());
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("# Failed to get Input / Output : "+ e.getMessage());
            System.exit(-1);
        } catch (NoSuchAlgorithmException|NoSuchPaddingException e) {
            System.err.println("# Failed to load AES encryptor : "+ e.getMessage());
            System.exit(-1);
        }
    }

}