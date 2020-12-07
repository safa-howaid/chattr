import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

public class Client {
    private static final String SERVER_NAME = "localhost";
    private static final int SERVER_PORT_NUMBER = Server.PORT_NUMBER;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private HashSet<String> onlineUsers;

    public boolean connectedToServer() {
        try {
            socket = new Socket(SERVER_NAME, SERVER_PORT_NUMBER);
            in = new BufferedReader(new InputStreamReader( socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            System.err.println("Unable to connect to server at port " + SERVER_PORT_NUMBER);
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

    public void runClient() {
        onlineUsers = new HashSet<>();
        try {
            Scanner scanner = new Scanner(System.in);
            String username;
            String response = "";
            while (!response.equals("Join successful")) {
                System.out.print("Enter a username to start: ");
                username = scanner.nextLine();
                out.println("join " + username);
                out.flush();
                response = in.readLine();
                System.out.println(response);
            }
            System.out.println("You have joined successfully");
            startEventReceiver();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startEventReceiver() {
        Thread receiverThread = new Thread(this::eventReceiverLoop);
        receiverThread.start();
    }

    private void eventReceiverLoop() {
        try {
            String line = in.readLine();
            while (line != null) {
                String[] event = line.split(" ");
                if (event.length > 0) {
                    String command = event[0];
                    if (command.equalsIgnoreCase("online")) {
                        onlineUsers.add(event[1]);
                        System.out.println(line);
                    }
                    else if (command.equalsIgnoreCase("offline")) {
                        onlineUsers.remove(event[1]);
                        System.out.println(line);
                    }
                }
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}