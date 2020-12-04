import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_NAME = "localhost";
    private static final int SERVER_PORT_NUMBER = Server.PORT_NUMBER;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public boolean connectedToServer() {
        try {
            socket = new Socket(SERVER_NAME, SERVER_PORT_NUMBER);
            in = new BufferedReader(new InputStreamReader( socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            System.out.println("Unable to connect to server at port " + SERVER_PORT_NUMBER);
        }
        return false;
    }

    public void runClientEcho() {
        try {
            Scanner scanner = new Scanner(System.in);
            String line;
            while (true) {
                System.out.print("Enter a message or quit: ");
                line = scanner.nextLine();
                out.println(line);
                out.flush();
                if (line.equals("quit")) {
                    break;
                }
                System.out.println(in.readLine());
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}