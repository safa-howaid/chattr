import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static final String SERVER_NAME = "localhost";
    private static final int SERVER_PORT_NUMBER = 7878;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ObservableList<String> onlineUsers;
    private ObservableList<Message> messages;
    private String username;
    private Thread receiverThread;

    public Client() {
        onlineUsers = FXCollections.observableArrayList();
        messages = FXCollections.observableArrayList();
        username = null;
    }
    /*
        Tries to connect to Server and initializes I/O streams.
        Returns true if a connection was made. Else, returns false.
     */
    public boolean connectedToServer() {
        try {
            socket = new Socket(SERVER_NAME, SERVER_PORT_NUMBER);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            return true;
        } catch (IOException e) {
            System.err.println("Unable to connect to server at port " + SERVER_PORT_NUMBER);
        }
        return false;
    }

    /*
        Sends the Server a request to join with the given username.
        If successful, updates client's username attribute and
        updates the chat and user lists with the information retrieved from
        the Server's response. Return true if successful. Else, returns false.
     */
    public boolean attemptJoin(String username) {
        try {
            out.writeObject(new Join(username));
            Object response  = in.readObject();

            if (response instanceof Success) {
                this.username = username;
                onlineUsers = FXCollections.observableArrayList(((Success) response).getOnlineUsers());
                messages = FXCollections.observableArrayList(((Success) response).getMessages());
                return true;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
        Creates a message, sends it to the server, and adds it to the message list.
     */
    public void sendMessage(String message) {
        try {
            Message newMessage = new Message(username, message);
            out.writeObject(newMessage);
            messages.add(newMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void leaveChat() {
        try {
            if (username != null) {
                out.writeObject(new Leave(username));
                receiverThread.interrupt();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        Starts a new thread for receiving events
     */
    public void startEventReceiver() {
        receiverThread = new Thread(this::eventReceiverLoop);
        receiverThread.start();
    }

    /*
        Keeps trying to accept events and handles each event accordingly.
     */
    private void eventReceiverLoop() {
        try {
            Object event;
            while (true) {
                event = in.readObject();

                if (event instanceof Join) {
                    onlineUsers.add(((Join) event).getSource());
                    messages.add(new Message("SERVER", ((Join) event).getSource() + " has joined"));
                }
                else if (event instanceof Leave) {
                    onlineUsers.remove(((Leave) event).getSource());
                    messages.add(new Message("SERVER", ((Leave) event).getSource() + " has left"));
                }
                else if (event instanceof Message) {
                    messages.add((Message) event);
                }

                System.out.println(event);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String> getOnlineUsers() {
        return onlineUsers;
    }

    public ObservableList<Message> getMessages() {
        return messages;
    }

    public String getUsername() {
        return username;
    }
}