import java.io.Serializable;

public class Leave extends ClientEvent implements Serializable {
    String username;
    public Leave(String username) {
        this.username = username;
    }
}
