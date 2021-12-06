package Client;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;

public class UserCell extends ListCell<String> {

    protected void updateItem(String username, boolean empty) {
        super.updateItem(username, empty);

        if (username == null || empty) {
            setGraphic(null);
            setItem(null);
            return;
        }

        BorderPane cell = new BorderPane();
        Image image = new Image(new File("onlineStatus.png").toURI().toString());
        ImageView onlineImage = new ImageView(image);
        Label user = new Label();
        user.setText(username);
        user.setStyle("-fx-font: bold 14 arial;");
        cell.setCenter(user);
        cell.setLeft(onlineImage);
        setGraphic(cell);
    }
}
