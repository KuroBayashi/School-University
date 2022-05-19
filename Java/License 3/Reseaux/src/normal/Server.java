package normal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    private final static int SERVER_PORT = 4444;

    public static void main(String[] args) {
        try (
            ServerSocket server = new ServerSocket(Server.SERVER_PORT);
        ) {
            System.out.println("# Server : Successfully started.");

            try (
                Socket client = server.accept();
            ) {
                System.out.println("# Server : Client connected.");

                Thread writerThread = new Thread(new WriterThread(client)); writerThread.start();
                Thread readerThread = new Thread(new ReaderThread(client)); readerThread.start();

                while (writerThread.isAlive() && readerThread.isAlive());
            }

        } catch (IOException e) {
            System.err.println("# Failed to get Input / Output : " + e.getMessage());
            System.exit(-1);
        }
    }
}
