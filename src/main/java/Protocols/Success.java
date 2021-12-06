package Protocols;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;

public class Success implements Serializable {
    private HashSet<String> onlineUsers;
    private LinkedList<Message> messages;

    public Success(HashSet<String> onlineUsers, LinkedList<Message> messages) {
        this.onlineUsers = onlineUsers;
        this.messages = messages;
    }

    public HashSet<String> getOnlineUsers() {
        return onlineUsers;
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }

    // String representation of the Success response. Used for debugging.
    @Override
    public String toString() {
        return "Join successful, here is the list other users currently online" + onlineUsers;
    }
}
