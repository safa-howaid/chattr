import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class ChatroomView extends Pane {
    private ListView<Message> chatList;
    private ListView<String> userList;
    private TextField newMessage;
    private Button sendButton;
    private Label chatBoxLabel;
    private Label userListLabel;
    private Label usernameLabel;
    private Button LeaveButton;
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

        sendButton = new Button("Send");
        sendButton.setStyle("-fx--font: 22 arial;");
        sendButton.relocate(pixelLength(7.9),pixelWidth(6.1));
        sendButton.setPrefSize(pixelLength(9.3)-pixelLength(7.9),pixelWidth(6.8)-pixelWidth(6.1));
        sendButton.setDisable(true);

        chatBoxLabel = new Label("Chat");
        chatBoxLabel.setStyle("-fx--font: bold 32 arial;");
        chatBoxLabel.relocate(pixelLength(4.6),pixelWidth(0.4));

        userListLabel = new Label("Online Users");
        userListLabel.setStyle("-fx--font: bold 32 arial;");
        userListLabel.relocate(pixelLength(10.8),pixelWidth(0.4));

        LeaveButton = new Button("Leave");
        LeaveButton.setStyle("-fx--font: 22 arial; -fx--fill: rgb(220,0,0);");
        LeaveButton.setPrefSize(pixelLength(11)-pixelLength(7.9),pixelWidth(6.6)-pixelWidth(6.1));
        LeaveButton.relocate(pixelLength(10.1),pixelWidth(6.3));

        usernameLabel = new Label("");
        usernameLabel.setStyle("-fx--font: bold 22 arial;");
        usernameLabel.relocate(pixelLength(9.8),pixelWidth(5.9));

        getChildren().addAll(sendButton,chatList,userList,newMessage,chatBoxLabel,userListLabel,usernameLabel,LeaveButton);
    }

    public double pixelWidth(double num){
        return 475.0*num/7.1;
    }

    public double pixelLength(double num){
        return 700*num/14;
    }

    public Button getSendButton() {
        return sendButton;
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
        Platform.runLater(() -> chatList.setItems(FXCollections.observableList(model.getMessages())));

    }
    public void updateUserList() {
        Platform.runLater(() -> userList.setItems(FXCollections.observableArrayList(model.getOnlineUsers())));

    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public Button getLeaveButton() {
        return LeaveButton;
    }
}
