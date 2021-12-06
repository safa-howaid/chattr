package Protocols;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class Join extends ClientEvent implements Serializable {

    public Join(String username) {
        super(username);
    }

    // String representation of the Join request. Used for debugging.
    @Override
    public String toString() {
        return "Attempt to join with the username '" + getSource() + "' at " +
                getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
