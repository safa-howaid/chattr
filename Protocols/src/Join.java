import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class Join extends ClientEvent implements Serializable {
    String username;
    public Join(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Attempt to join with the username '" + username + "' at " + timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
