import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class UserCell extends ListCell<String> {

    protected void updateItem(String username, boolean empty) {
        super.updateItem(username, empty);

        if (username == null || empty) {
            setGraphic(null);
            setItem(null);
            return;
        }

        BorderPane cell = new BorderPane();
        ImageView onlineImage = new ImageView(new Image("onlineStatus.png",10,10,false,true));
        Label user = new Label();
        user.setText(username);
        user.setStyle("-fx-font: bold 14 arial;");
        cell.setCenter(user);
        cell.setLeft(onlineImage);
        setGraphic(cell);
    }
}
