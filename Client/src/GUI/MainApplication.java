import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class MainApplication extends Application {
    private Client client;
    private StartupView startupView;
    private ChatroomView chatroomView;

    @Override
    public void start(Stage primaryStage) {
        client = new Client();
        startupView = new StartupView();
        chatroomView = new ChatroomView(client);

        Scene chatroomScene = new Scene(chatroomView, 700, 475);
        Scene startupScene = new Scene(startupView,700,475);

        primaryStage.setScene(startupScene);
        primaryStage.setResizable(false);
        primaryStage.show();

        startupView.getUsernameTextField().setOnKeyReleased(this::handleJoinButtonDisabling);

        startupView.getUsernameTextField().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                startupView.getJoinChatButton().fire();
            }
        });

        startupView.getJoinChatButton().setOnAction(event -> {
            if(handleJoinChat()){
                client.startEventReceiver();
                primaryStage.setScene(chatroomScene);
                moveToChatroom();

            }
        });

        chatroomView.getNewMessage().setOnKeyReleased(this::handleSendButtonDisabling);

        chatroomView.getLeaveButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                client.leaveChat();
                primaryStage.setScene(startupScene);
            }
        });

        chatroomView.getNewMessage().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                if (chatroomView.getNewMessage().getText().length() > 0) {
                    handleSendMessage();
                }
            }
        });

    }

    @Override
    public void stop() {
        client.leaveChat();
        Platform.exit();
        System.exit(0);
    }

    public void moveToChatroom() {
        chatroomView.updateMessages();
        chatroomView.updateUserList();

        chatroomView.getUsernameLabel().setText("You're logged in as: " + client.getUsername());

        chatroomView.getChatList().setCellFactory(messageListView -> new MessageCell(chatroomView));

        chatroomView.getSendButton().setOnAction(actionEvent -> handleSendMessage());

        chatroomView.getUserList().setCellFactory(stringListView -> new UserCell());
        client.getMessages().addListener((ListChangeListener<Message>) change -> chatroomView.updateMessages());
        client.getOnlineUsers().addListener((ListChangeListener<String>) change -> chatroomView.updateUserList());
    }

    public boolean handleJoinChat() {

        if (client.connectedToServer()) {
            if (!client.attemptJoin(startupView.getUsernameTextField().getText())){
                startupView.getUsernameTextField().setText("");
                Alert invalidUsername = new Alert(Alert.AlertType.ERROR, "Username is already used or is invalid, please try again.");
                invalidUsername.showAndWait();
                return false;
            }
            return true;

        }
        Alert unableToConnect = new Alert(Alert.AlertType.ERROR, "Unable to connect to server, make sure that server is running.");
        unableToConnect.showAndWait();
        return false;

    }

    public void handleSendMessage(){
        client.sendMessage(chatroomView.getNewMessage().getText());
        chatroomView.getNewMessage().setText("");
    }

    private void handleSendButtonDisabling(KeyEvent keyEvent) {
        chatroomView.getSendButton().setDisable(chatroomView.getNewMessage().getText().length() <= 0);
    }

    private void handleJoinButtonDisabling(KeyEvent keyEvent) {
        startupView.getJoinChatButton().setDisable(startupView.getUsernameTextField().getText().length() <= 0);
    }

}
