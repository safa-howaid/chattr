import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class Client {
    private static final String SERVER_NAME = "localhost";
    private static final int SERVER_PORT_NUMBER = 7878;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private HashSet<String> onlineUsers;
    private ArrayList<Message> messages;
    private String username;

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

    public boolean attemptJoin(String username) {
        try {
            out.writeObject(new Join(username));

            Object response  = in.readObject();
            System.out.println(response);
            if (response instanceof Success) {
                this.username = username;
                onlineUsers = ((Success) response).onlineUsers;
                messages = ((Success) response).messages;
                return true;
            } // the username will not be deleted in the server, but it will be deleted in other threads
            else return false;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

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
                Thread.sleep(5000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startEventReceiver() {
        // Start a new thread for receiving events
        Thread receiverThread = new Thread(this::eventReceiverLoop);
        receiverThread.start();
    }

    private void eventReceiverLoop() {
        try {
            Object event;
            while (true) {
                event = in.readObject();

                if (event instanceof Join) {
                    onlineUsers.add(((Join) event).username);
                    messages.add(new Message("SERVER", ((Join) event).username + " has joined"));
//                    Display join system message on console for testing
//                    System.out.println("SERVER: " + ((Join) event).username + " has joined");
                }
                else if (event instanceof Leave) {
                    onlineUsers.remove(((Leave) event).username);
                    messages.add(new Message("SERVER", ((Leave) event).username + " has left"));
//                    Display leave system message on console for testing
//                    System.out.println("SERVER: " + ((Join) event).username + " has joined");
                }
                else if (event instanceof Message) {
                    messages.add((Message) event);
//                    Display message received on console for testing
//                    System.out.println(((Message) event).username + ": " + ((Message) event).message);
                }
                System.out.println(event);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public HashSet<String> getOnlineUsers() {
        return onlineUsers;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}