package Client;

import Protocols.Message;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;

public class MessageCell extends ListCell<Message> {

    private ChatroomView view;

    public MessageCell(ChatroomView view) {
        this.view = view;
    }

    protected void updateItem(Message message, boolean empty) {
        super.updateItem(message, empty);

        if (message == null || empty) {
            setGraphic(null);
            return;
        }

        BorderPane cell = new BorderPane();
        Label username = new Label();
        Label msg = new Label();
        Label timestamp = new Label();
        HBox userAndMessage = new HBox();

        userAndMessage.getChildren().addAll(username,msg);

        username.setStyle("-fx-font: bold 14 arial;");
        timestamp.setStyle("-fx-text-fill: rgb(210,210,210);");

        username.setText(message.getSource() + ":  ");
        msg.setText(message.getMessage());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a | LLLdd");
        timestamp.setText(message.getTimestamp().format(formatter));

        cell.setLeft(userAndMessage);
        cell.setRight(timestamp);
        cell.setMaxWidth(view.getChatList().getWidth()-20);
        msg.setMaxWidth(view.getChatList().getWidth()-190);
        msg.setWrapText(true);

        if (message.getSource().equals("SERVER")) {
            username.setStyle("-fx-font: bold 14 arial; -fx-text-fill: rgb(210,0,0);");
            msg.setStyle("-fx-font: bold 14 arial; -fx-text-fill: rgb(210,0,0);");
        }

        setGraphic(cell);
    }
}
