import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Success implements Serializable {
    ArrayList<String> times;
    HashSet<String> onlineUsers;
    ArrayList<Message> messages;


    public Success(HashSet<String> onlineUsers, ArrayList<Message> messages, ArrayList<String> times) {
        this.onlineUsers = onlineUsers;
        this.messages = messages;
        this.times = times;
    }

    @Override
    public String toString() {
        return "Join successful, here is the list of online users" + onlineUsers;
    }
}
