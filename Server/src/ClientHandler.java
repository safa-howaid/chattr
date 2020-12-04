import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        clientSocket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runServerEcho() {
        try {
            String line = in.readLine();
            while (line != null) {
                if(line.equals("quit")) {
                    break;
                }
                out.println("The server received the following message: " + line);
                out.flush();
                line = in.readLine();
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        runServerEcho();
    }
}