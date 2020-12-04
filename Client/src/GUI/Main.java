package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.imageio.plugins.tiff.BaselineTIFFTagSet;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {


        Pane mainPane = new Pane();
        Label title = new Label("Internet Relay Chat");
        title.relocate(700*3.5/14,475/7.1);
        title.setStyle("-fx-font: 42 arial; -fx-base: rgb(170,0,0); -fx-text-fill: rgb(0,0,0);");

        Label input = new Label("Enter a username to start");
        input.relocate(700*4.5/14 + 15,475*2.8/7.1);
        input.setStyle("-fx-font: 22 arial; -fx-base: rgb(170,0,0); -fx-text-fill: rgb(0,0,0);");

        TextField username = new TextField();
        username.relocate(700*3.9/14 + 17 + 15,475*3.6/7.1);
        username.setStyle("-fx-font: 22 arial; -fx-base: rbg(75,4,32); -fx-text-fill: rgb(0,0,0);");
        username.setPrefHeight(475*0.8/7.1);

        Button join_chat = new Button("Join Chat");
        join_chat.relocate(500*9.9/14+700.0/4-50,475*5.5/7.1);
        join_chat.setPrefSize(700*2.9/14,475*0.8/7.1);
        join_chat.setStyle("-fx-font: 25 arial;");

        mainPane.getChildren().add(title);
        mainPane.getChildren().add(input);
        mainPane.getChildren().add(username);
        mainPane.getChildren().add(join_chat);

        Scene scene = new Scene(mainPane,700,475);
        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
