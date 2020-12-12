import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Success implements Serializable {
    HashSet<String> onlineUsers;
    ArrayList<Message> messages;

    public Success(HashSet<String> onlineUsers, ArrayList<Message> messages) {
        this.onlineUsers = onlineUsers;
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Join successful, here is the list of online users" + onlineUsers;
    }
}
