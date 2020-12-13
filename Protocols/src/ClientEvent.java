import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class ClientEvent implements Serializable {
    LocalDateTime timestamp;

    public ClientEvent() {
        timestamp = LocalDateTime.now();
    }
}
