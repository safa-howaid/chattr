import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class StartupView extends Pane {
    private TextField username;
    private Button join_chat;
    private Client model;

    public StartupView(Client model) {
        this.model = model;

        Label title = new Label("Internet Relay Chat");
        title.relocate(700*3.5/14,475/7.1);
        title.setStyle("-fx-font: 42 arial; -fx-base: rgb(170,0,0); -fx-text-fill: rgb(0,0,0);");

        Label input = new Label("Enter a username to start");
        input.relocate(700*4.5/14 + 15,475*2.8/7.1);
        input.setStyle("-fx-font: 22 arial; -fx-base: rgb(170,0,0); -fx-text-fill: rgb(0,0,0);");

        username = new TextField();
        username.relocate(700*3.9/14 + 17 + 15,475*3.6/7.1);
        username.setStyle("-fx-font: 22 arial; -fx-text-fill: rgb(0,0,0);");
        username.setPrefHeight(475*0.8/7.1);

        join_chat = new Button("Join Chat");
        join_chat.relocate(500*9.9/14+700.0/4-50,475*5.5/7.1);
        join_chat.setPrefSize(700*2.9/14,475*0.8/7.1);
        join_chat.setStyle("-fx-font: 25 arial;");

        getChildren().add(title);
        getChildren().add(input);
        getChildren().add(username);
        getChildren().add(join_chat);
    }

    public Button getJoin_chat() {
        return join_chat;
    }

    public TextField getUsername() {
        return username;
    }

}
