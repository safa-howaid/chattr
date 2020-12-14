import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;
import java.time.format.DateTimeFormatter;

public class ChatroomView extends Pane {
    private ListView<Message> chatList;
    private ListView<String> userList;
    private ListView<String> timeList;
    private String[] messages;
    private String[] users;
    private String[] times;
    private ObservableList<Message> obsMessages;
    private ObservableList<String> obsUsers;
    private TextField newMessage;
    private Button send_button;
    private Label chatBoxLabel;
    private Label userListLabel;
    private Label usernameLabel;
    private Button LeaveButton;
    private Client model;
    private ImageView iv;

    public ChatroomView(Client model){
        this.model = model;

        /*BackgroundImage myBI= new BackgroundImage(new Image("file:image2.jpg",700,475,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        setBackground(new Background(myBI));*/
        chatList = new ListView<>();
        messages = new String[]{"User1:Hey!", "User2:Hey! How are you?"};

        userList = new ListView<>();
        users = new String[]{"User1", "User2"};

        timeList = new ListView<>();
        times = new String[]{};


        chatList.relocate(pixelLength(0.6),pixelWidth(0.8));
        chatList.setPrefSize(pixelLength(7.3)-pixelLength(0.6),pixelWidth(5.8)-pixelWidth(0.8));

        userList.relocate(pixelLength(9.7),pixelWidth(0.8));
        userList.setPrefSize(pixelLength(13.3)-pixelLength(9.7), pixelWidth(5.8)-pixelWidth(0.8));

        timeList.relocate(pixelLength(7),pixelWidth(0.8));
        timeList.setPrefSize(pixelLength(9.3)-pixelLength(7),pixelWidth(5.8)-pixelWidth(0.8));

        newMessage = new TextField();
        newMessage.relocate(pixelLength(0.6),pixelWidth(6.1));
        newMessage.setPrefSize(pixelLength(7)-pixelLength(0.6),pixelWidth(6.8)-pixelWidth(6.1));

        send_button = new Button("Send");
        send_button.setStyle("-fx--font: 22 arial;");
        send_button.relocate(pixelLength(7.9),pixelWidth(6.1));
        send_button.setPrefSize(pixelLength(9.3)-pixelLength(7.9),pixelWidth(6.8)-pixelWidth(6.1));

        LeaveButton = new Button("Leave");
        LeaveButton.setStyle("-fx--font: 22 arial;");
        LeaveButton.relocate(pixelLength(12.6),pixelWidth(0.1));

        chatBoxLabel = new Label("Chat");
        chatBoxLabel.setStyle("-fx--font: 32 arial;");
        chatBoxLabel.relocate(pixelLength(4.6),pixelWidth(0.4));

        userListLabel = new Label("Online Users");
        userListLabel.setStyle("-fx--font: 32 arial;");
        userListLabel.relocate(pixelLength(10.8),pixelWidth(0.4));

        usernameLabel = new Label("");
        usernameLabel.setStyle("-fx--font: 2ali2 Times New Roman;");
        usernameLabel.relocate(pixelLength(0.4),pixelWidth(0.3));


        getChildren().addAll(send_button,chatList,userList,newMessage,chatBoxLabel,userListLabel,usernameLabel,LeaveButton,timeList);

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

    public void update() {

        userList.setItems(FXCollections.observableArrayList(model.getOnlineUsers()));
        chatList.setItems(FXCollections.observableList(model.getMessages()));
        timeList.setItems(FXCollections.observableArrayList(model.getTimes()));
    }

    public ObservableList<Message> getObsMessages() {
        return obsMessages;
    }

    public ObservableList<String> getObsUsers() {
        return obsUsers;
    }

    public void setObsMessages(ObservableList<Message> message) {
        obsMessages = message;
    }

    public void setObsUsers(ObservableList<String> user) {
        obsUsers = user;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public Button getLeaveButton() {
        return LeaveButton;
    }
}
