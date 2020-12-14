import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class Message extends ClientEvent implements Serializable {
    String username;
    String message;
    String time;

    public Message(String username, String message) {
        this.username = username;
        this.message = message;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a | LLLdd");
        time = timestamp.format(formatter);
    }

    @Override
    public String toString() {

        return username + ": " + message;
    }

    public String getTime() {
        return time;
    }
}
