package normal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ReaderThread implements Runnable {

    private Socket socket;

    ReaderThread (Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            String received;
            while (null != (received = reader.readLine())) {

                System.out.println("Received : "+ received);
            }

        } catch (IOException e) {
            System.err.println("# Reader : Failed to get Input / Output : "+ e.getMessage());
        }
    }
}
