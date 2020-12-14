import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;


public class MainApplication extends Application {
    Client client;
    StartupView startupView;
    ChatroomView chatroomView;

    @Override
    public void start(Stage primaryStage) {
        client = new Client();
        startupView = new StartupView();

        primaryStage.setScene(new Scene(startupView,700,475));
        primaryStage.setResizable(false);
        primaryStage.show();

        startupView.getJoin_chat().setOnAction(event -> {
            if(handleJoinChat()){
                client.startEventReceiver();
                chatroomView = new ChatroomView(client);
                primaryStage.setScene(new Scene(chatroomView,700,475));
                chatroomView.updateMessages();
                chatroomView.updateUserList();

                chatroomView.getChatList().setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
                    @Override
                    public ListCell<Message> call(ListView<Message> param) {
                        return new ListCell<>() {

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

                                HBox com = new HBox();
                                com.getChildren().addAll(username,msg);

                                username.setStyle("-fx-font: bold 14 arial;");
                                timestamp.setStyle("-fx-text-fill: rgb(210,210,210);");

                                username.setText(message.username + ":  ");

                                msg.setText(message.message);
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a | LLLdd");
                                timestamp.setText(message.timestamp.format(formatter));

                                cell.setLeft(com);
                                cell.setRight(timestamp);


                                cell.setMaxWidth(chatroomView.getChatList().getWidth()-20);
                                msg.setMaxWidth(chatroomView.getChatList().getWidth()-190);
                                msg.setWrapText(true);


                                if (message.username.equals("SERVER")) {
                                    username.setStyle("-fx-font: bold 14 arial; -fx-text-fill: rgb(210,0,0);");
                                    msg.setStyle("-fx-font: bold 14 arial; -fx-text-fill: rgb(210,0,0);");
                                }

                                setGraphic(cell);

                            }
                        };
                    }
                });

                chatroomView.getSend_button().setOnAction(actionEvent -> handleSendMessage());
                // Run the specified Runnable on the JavaFX Application Thread at some unspecified time in the future.
                // Because changes to the the view cannot be done on a separate thread

            }
        });

    }

    @Override
    public void stop() {
        client.leaveChat();
    }

    public boolean handleJoinChat() {

        if (client.connectedToServer()) {
            if (!client.attemptJoin(startupView.getUsername().getText())){
                startupView.getUsername().setText("");
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

    private void handle(KeyEvent keyEvent) {
        chatroomView.getSend_button().setDisable(chatroomView.getNewMessage().getText().length() <= 0);
    }

    private void run() {
        //update application thread
        client.getMessages().addListener((ListChangeListener<Message>) change -> chatroomView.updateMessages());
        client.getOnlineUsers().addListener((ListChangeListener<String>) change -> chatroomView.updateUserList());
    }
}
