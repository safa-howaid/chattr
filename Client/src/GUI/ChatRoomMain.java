package GUI;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.imageio.plugins.tiff.BaselineTIFFTagSet;

public class ChatRoomMain extends Application {

    @Override
    public void start(Stage primaryStage) {


        Pane mainPane = new Pane();
        ListView<String> chatList = new ListView<String>();
        String[] messages = {"User1:Hey!", "User2:Hey! How are you?"};

        ListView<String> userList = new ListView<String>();
        String[] users = {"User1", "User2"};

        ObservableList<String> obsMessages = FXCollections.observableArrayList(messages);
        ObservableList<String> obsUsers = FXCollections.observableArrayList(users);


        chatList.setItems(obsMessages);
        chatList.relocate(pixelLength(0.6),pixelWidth(0.8));
        chatList.setPrefSize(pixelLength(9.3)-pixelLength(0.6),pixelWidth(5.8)-pixelWidth(0.8));

        userList.setItems(obsUsers);
        userList.relocate(pixelLength(9.7),pixelWidth(0.8));
        userList.setPrefSize(pixelLength(13.3)-pixelLength(9.7), pixelWidth(5.8)-pixelWidth(0.8));


        TextField newMessage = new TextField();
        newMessage.relocate(pixelLength(0.6),pixelWidth(6.1));
        newMessage.setPrefSize(pixelLength(7.6)-pixelLength(0.6),pixelWidth(6.8)-pixelWidth(6.1));

        Button send_button = new Button("Send");
        send_button.setStyle("-fx--font: 22 arial;");
        send_button.relocate(pixelLength(7.9),pixelWidth(6.1));
        send_button.setPrefSize(pixelLength(9.3)-pixelLength(7.9),pixelWidth(6.8)-pixelWidth(6.1));

        Label chatBoxLabel = new Label("Chat");
        chatBoxLabel.setStyle("-fx--font: 32 arial;");
        chatBoxLabel.relocate(pixelLength(4.6),pixelWidth(0.4));

        Label userListLabel = new Label("list of users");
        userListLabel.setStyle("-fx--font: 32 arial;");
        userListLabel.relocate(pixelLength(9.8),pixelWidth(0.4));


        obsMessages.add("");

        mainPane.getChildren().addAll(send_button,chatList,userList,newMessage,chatBoxLabel,userListLabel);

/*
        mainPane.getChildren().add(title);
        mainPane.getChildren().add(input);
        mainPane.getChildren().add(username);
        mainPane.getChildren().add(join_chat);*/

        Scene scene = new Scene(mainPane,700,475);
        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public double pixelWidth(double num){
        return 475.0*num/7.1;
    }

    public double pixelLength(double num){
        return 700*num/14;
    }


    public static void main(String[] args) {
        launch(args);
    }
}

