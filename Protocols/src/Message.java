import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class Message extends ClientEvent implements Serializable {
    String username;
    String message;

    public Message(String username, String message) {
        this.username = username;
        this.message = message;
    }

    @Override
    public String toString() {
        return username + ": " + message;
    }

}
