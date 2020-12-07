import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private Server server;


    public ClientHandler(Socket socket, Server server) {
        clientSocket = socket;
        this.server = server;
        username = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestHandler() {
        try {
            String line = in.readLine();
            while (line != null) {
                String[] request = line.split(" ");
                if (request.length > 0) {
                    String command = request[0];
                    if (command.equalsIgnoreCase("join")) {
                        System.out.println(line);
                        handleJoinRequest(request);
                    }
                    else if (command.equalsIgnoreCase("leave")) {
                        handleLeaveRequest(request);
                    }
                    else {
                        out.println("Invalid command " + request[0]);
                    }
                    out.flush();
                }
                line = in.readLine();
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleJoinRequest(String[] request) {
        if (request.length == 2) {
            username = request[1];
            if (!server.getConnectionMap().containsKey(username)) {
                out.println("Join successful");
                out.flush();
                server.getConnectionMap().put(username,this);
                broadcastServerMessage("online " + username);

                for (ClientHandler client: server.getConnectionMap().values()) {
                    if (client != this) {
                        out.println("online " + client.username);
                        client.out.flush();
                    }
                }
            }
            else {
                out.println("Username " + username + " already exists.");
                out.flush();
            }

        }
        else {
            out.println("Invalid use of command: join <username>");
            out.flush();

        }
    }

    private void handleLeaveRequest(String[] request) throws IOException {
        if (request.length == 2) {
            username = request[1];
            if (server.getConnectionMap().containsKey(username)) {
                out.println("Leave successful");
                out.flush();
                server.getConnectionMap().remove(username);
                broadcastServerMessage("offline " + username);
            }
            else {
                out.println("Username " + username + " does not exist.");
                out.flush();
            }
        }
        else {
            out.println("Invalid use of command: leave <username>");
            out.flush();
        }
    }

    public void broadcastServerMessage(String message) {
        for (ClientHandler client: server.getConnectionMap().values()) {
            if (client != this) {
                client.out.println(message);
                client.out.flush();
            }
        }
    }

    public String getUsername() {
        return username;
    }

    /**
     * A method that was used for testing server/client connections
     */
    private void runServerEcho() {
        try {
            String line = in.readLine();
            while (line != null && !line.equals("quit")) {
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
        requestHandler();
    }
}