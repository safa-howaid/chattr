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

import javax.swing.text.html.ImageView;
import java.time.format.DateTimeFormatter;


public class MainApplication extends Application {
    //create a startupscene
    //get the input of the user
    //when GUI gets the username, make the loading view
    //send the username to server
    //wait until get the acceptance
    //if denied set the field bound to red
    //add a label under the button, invalid username
    //if accepted
    //change the scene to chatroom
    Client model;
    StartupView startupView;
    ChatroomView chatroomView;

    @Override
    public void start(Stage primaryStage) {
        model = new Client();
        startupView = new StartupView();
        chatroomView = new ChatroomView(model);

        Scene chatroomScene = new Scene(chatroomView, 700, 475);
        Scene startupScene = new Scene(startupView,700,475);


        primaryStage.setScene(startupScene);
        primaryStage.setResizable(false);
        primaryStage.show();


        chatroomView.getNewMessage().setOnKeyReleased(this::handle);

        chatroomView.getLeaveButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.leaveChat();
                primaryStage.setScene(startupScene);
            }
        });

        startupView.getJoin_chat().setOnAction(event -> {
            if(handleJoinChat()){
                model.startEventReceiver();
                primaryStage.setScene(chatroomScene);
                chatroomView.getUsernameLabel().setText("You logged in as: "+model.getUsername());
                chatroomView.update();

                // Added to stop JavaFX thread error
                Platform.runLater(this::run);



            }
        });

        chatroomView.getNewMessage().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                if (chatroomView.getNewMessage().getText().length() > 0) {
                    handleSendMessage();
                }
            }
        });

        chatroomView.getSend_button().setOnAction(actionEvent -> handleSendMessage());
    }

    @Override
    public void stop() {
        model.leaveChat();
    }

    public boolean handleJoinChat() {

        if (model.connectedToServer()) {
            if (!model.attemptJoin(startupView.getUsername().getText())){
                startupView.getUsername().setText("");
                Alert invalidUsername = new Alert(Alert.AlertType.ERROR, "Username is already used, please try again.");
                invalidUsername.showAndWait();
                return false;
            }
            return true;

        }
        return false;

    }

    public void handleSendMessage(){
        model.sendMessage(chatroomView.getNewMessage().getText());
        chatroomView.getNewMessage().setText("");
    }

    private void handle(KeyEvent keyEvent) {
        chatroomView.getSend_button().setDisable(chatroomView.getNewMessage().getText().length() <= 0);
    }

    private void run() {
//update application thread
        model.getMessages().addListener((ListChangeListener<Message>) change -> chatroomView.update());

        model.getOnlineUsers().addListener((ListChangeListener<String>) change -> chatroomView.update());

        model.getTimes().addListener((ListChangeListener<String>) change -> chatroomView.update());
    }
}
