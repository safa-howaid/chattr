import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class MainGUI extends Application {
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
    StartupViewGUI view1;
    ChatRoomGUI view2;

    @Override
    public void start(Stage primaryStage) {
        model = new Client();
        view1 = new StartupViewGUI(model);
        view2 = new ChatRoomGUI(model);



        primaryStage.setScene(new Scene(view1,700,475));
        primaryStage.setResizable(false);
        primaryStage.show();

        view1.getJoin_chat().setOnAction(event -> {
            if(handle_join_chat()){
                setView2();
                primaryStage.setScene(new Scene(view2,700,475));
                primaryStage.setResizable(false);

            }
        });

        view2.getSend_button().setOnAction(actionEvent -> handle_sendMessage());
    }

    @Override
    public void stop() {
        model.leaveChat();
    }

    public boolean handle_join_chat() {
        //create the loading circle

        if (model.connectedToServer()) {
            if (!model.attemptJoin(view1.getUsername().getText())){
                view1.getUsername().setText("");
                Alert invalidUsername = new Alert(Alert.AlertType.ERROR, "Username in use, please try again.");
                invalidUsername.showAndWait();
                return false;
            }
            return true;

        }
        return false;

    }


    public void handle_sendMessage(){
        model.sendMessage(view2.getNewMessage().getText());
        view2.getNewMessage().setText("");
    }

    public void setView2() {
        view2.getUserList().setItems(FXCollections.observableArrayList(model.getOnlineUsers()));
        view2.getChatList().setItems(FXCollections.observableArrayList(model.getMessages()));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
