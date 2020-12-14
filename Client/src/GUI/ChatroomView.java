import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;

public class ChatroomView extends Pane {
    private ListView<Message> chatList;
    private ListView<String> userList;
    private ObservableList<Message> obsMessages;
    private ObservableList<String> obsUsers;
    private TextField newMessage;
    private Button send_button;
    private Label chatBoxLabel;
    private Label userListLabel;
    private Client model;

    public ChatroomView(Client model){
        this.model = model;
        chatList = new ListView<>();
        userList = new ListView<>();

        chatList.relocate(pixelLength(0.6),pixelWidth(0.8));
        chatList.setPrefSize(pixelLength(9.3)-pixelLength(0.6),pixelWidth(5.8)-pixelWidth(0.8));


        userList.relocate(pixelLength(9.7),pixelWidth(0.8));
        userList.setPrefSize(pixelLength(13.3)-pixelLength(9.7), pixelWidth(5.8)-pixelWidth(0.8));


        newMessage = new TextField();
        newMessage.relocate(pixelLength(0.6),pixelWidth(6.1));
        newMessage.setPrefSize(pixelLength(7.6)-pixelLength(0.6),pixelWidth(6.8)-pixelWidth(6.1));


        send_button = new Button("Send");
        send_button.setStyle("-fx--font: 22 arial;");
        send_button.relocate(pixelLength(7.9),pixelWidth(6.1));
        send_button.setPrefSize(pixelLength(9.3)-pixelLength(7.9),pixelWidth(6.8)-pixelWidth(6.1));
        send_button.setDisable(true);

        chatBoxLabel = new Label("Chat");
        chatBoxLabel.setStyle("-fx--font: 32 arial;");
        chatBoxLabel.relocate(pixelLength(4.6),pixelWidth(0.4));

        userListLabel = new Label("Online Users");
        userListLabel.setStyle("-fx--font: 32 arial;");
        userListLabel.relocate(pixelLength(10.8),pixelWidth(0.4));

        getChildren().addAll(send_button,chatList,userList,newMessage,chatBoxLabel,userListLabel);

    }

    public double pixelWidth(double num){
        return 475.0*num/7.1;
    }

    public double pixelLength(double num){
        return 700*num/14;
    }

    public Button getSend_button() {
        return send_button;
    }

    public ListView<Message> getChatList() {
        return chatList;
    }

    public ListView<String> getUserList() {
        return userList;
    }

    public TextField getNewMessage() {
        return newMessage;
    }

    public void updateMessages() {
        chatList.setItems(FXCollections.observableList(model.getMessages()));
    }
    public void updateUserList() {
        userList.setItems(FXCollections.observableArrayList(model.getOnlineUsers()));
    }
}
