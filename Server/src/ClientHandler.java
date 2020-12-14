import java.io.*;
import java.net.Socket;
import java.util.HashSet;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String username;
    private Server server;


    public ClientHandler(Socket socket, Server server) {
        clientSocket = socket;
        this.server = server;
        username = null;
        try {
            in  = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestHandler() {
        try {
            Object request;
            while (true) {
                request = in.readObject();

                if (request instanceof Join) {
                    handleJoinRequest((Join) request);
                }
                else if (request instanceof Leave) {
                    handleLeaveRequest((Leave) request);
                    break;
                }
                else if (request instanceof Message) {
                    handleMessageRequest((Message) request);
                }
                System.out.println(request);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleMessageRequest(Message request) {
        // Save the message to the server and forward it to all other clients
        try {
            server.getMessages().add(request);
            broadcastEvent(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleJoinRequest(Join request) throws IOException {
        if (!server.getConnectionMap().containsKey(request.username) && !request.username.equalsIgnoreCase("SERVER")) {

            // Set username and add user to server connections hashmap
            username = request.username;
            server.getConnectionMap().put(username,this);

            // Get all online users and current chat messages send them to the user that just joined
            HashSet<String> onlineUsers = new HashSet<>();
            for (ClientHandler client: server.getConnectionMap().values()) {
                if (client != this) {
                    onlineUsers.add(client.getUsername());
                }
            }
            out.writeObject(new Success(onlineUsers, server.getMessages()));

            // Tell all other clients that a user has joined
            broadcastEvent(request);
        }
        else {
            out.writeObject(new Fail());
        }
    }

    private void handleLeaveRequest(Leave request) throws IOException {
        if (server.getConnectionMap().containsKey(request.username)) {

            // Remove user from server connections hashmap
            server.getConnectionMap().remove(username);

            // Set username to null
            username = null;

            // Tell all other clients that a user has left
            broadcastEvent(request);

            // Close connection and resources
            in.close();
            out.close();
            clientSocket.close();
        }
    }


    public void broadcastEvent(ClientEvent event) throws IOException {
        // Go through each connected client and send the event to all of them except the current one
        for (ClientHandler client: server.getConnectionMap().values()) {
            if (client != this) {
                client.out.writeObject(event);
            }
        }
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void run() {
        requestHandler();
    }
}