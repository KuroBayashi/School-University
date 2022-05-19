package normal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class WriterThread implements Runnable {

    private Socket socket;

    WriterThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            PrintWriter writer = new PrintWriter(this.socket.getOutputStream(), true);
            BufferedReader prompt = new BufferedReader(new InputStreamReader(System.in));
        ) {
            String sent;
            while (null != (sent = prompt.readLine())) {

                writer.println(sent);
                writer.flush();

                if ("bye".equals(sent)) break;
            }

        } catch (IOException e) {
            System.err.println("# Writer : Failed to get Input / Output : "+ e.getMessage());
        }
    }
}
