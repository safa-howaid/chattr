import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class ClientEvent implements Serializable {
    private LocalDateTime timestamp;
    private String source;

    public ClientEvent(String source) {
        timestamp = LocalDateTime.now();
        this.source = source;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getSource() {
        return source;
    }
}
