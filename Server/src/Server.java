import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

public class Server {
    public static final int PORT_NUMBER = 7878;
    private HashMap<String, ClientHandler> current_connections;
    private LinkedList<Message> messages;

    public Server() {
        current_connections = new HashMap<>();
        messages = new LinkedList<>();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connection was established with " + socket);

                //Delegate handling each connection to a new thread
                ClientHandler clientHandler = new ClientHandler(socket, this);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, ClientHandler> getConnectionMap() {
        return current_connections;
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}