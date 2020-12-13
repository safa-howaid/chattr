import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


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
        startupView = new StartupView(model);
        chatroomView = new ChatroomView(model);



        primaryStage.setScene(new Scene(startupView,700,475));
        primaryStage.setResizable(false);
        primaryStage.show();

        startupView.getJoin_chat().setOnAction(event -> {
            if(handle_join_chat()){
                setView2();
                primaryStage.setScene(new Scene(chatroomView,700,475));
                primaryStage.setResizable(false);

            }
        });

        chatroomView.getSend_button().setOnAction(actionEvent -> handle_sendMessage());
    }

    @Override
    public void stop() {
        model.leaveChat();
    }

    public boolean handle_join_chat() {
        //create the loading circle

        if (model.connectedToServer()) {
            if (!model.attemptJoin(startupView.getUsername().getText())){
                startupView.getUsername().setText("");
                Alert invalidUsername = new Alert(Alert.AlertType.ERROR, "Username in use, please try again.");
                invalidUsername.showAndWait();
                return false;
            }
            return true;

        }
        return false;

    }


    public void handle_sendMessage(){
        model.sendMessage(chatroomView.getNewMessage().getText());
        chatroomView.getNewMessage().setText("");
    }

    public void setView2() {
        chatroomView.getUserList().setItems(FXCollections.observableArrayList(model.getOnlineUsers()));
        chatroomView.getChatList().setItems(FXCollections.observableArrayList(model.getMessages()));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
