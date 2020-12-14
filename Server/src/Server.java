import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

public class Server {
    private final int PORT_NUMBER = 7878;
    private HashMap<String, ClientHandler> currentConnections;
    private LinkedList<Message> messages;

    public Server() {
        currentConnections = new HashMap<>();
        messages = new LinkedList<>();
    }

    /*
        Repeatedly tries to accept connections. When a client gets connected,
         they get assigned a new thread to communicate with. This is to allow multiple clients
         to interact with the server simultaneously.
     */
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
        return currentConnections;
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}