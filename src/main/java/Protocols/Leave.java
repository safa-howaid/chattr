package Protocols;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class Leave extends ClientEvent implements Serializable {

    public Leave(String username) {
        super(username);
    }

    // String representation of the Leave request. Used for debugging.
    @Override
    public String toString() {
        return "Leave request from '" + getSource() + "' at " +
                getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
