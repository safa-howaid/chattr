import java.io.Serializable;

public class Message extends ClientEvent implements Serializable {
    String username;
    String message;

    public Message(String username, String message) {
        this.username = username;
        this.message = message;
    }
}
