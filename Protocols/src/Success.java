import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;

public class Success implements Serializable {
    HashSet<String> onlineUsers;
    LinkedList<Message> messages;

    public Success(HashSet<String> onlineUsers, LinkedList<Message> messages) {
        this.onlineUsers = onlineUsers;
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Join successful, here is the list of online users" + onlineUsers;
    }
}
