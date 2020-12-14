import java.io.Serializable;

public class Message extends ClientEvent implements Serializable {
    private String message;

    public Message(String source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    // String representation of the Message request. Used for debugging.
    @Override
    public String toString() {
        return getSource() + ": " + message;
    }

}
