import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class StartupView extends Pane {
    private TextField usernameTextField;
    private Button joinChatButton;

    public StartupView() {
        Label title = new Label("Chattr");
        title.relocate(700*5.3/14,475/7.1);
        title.setStyle("-fx-font: bold 62 arial; -fx-base: rgb(170,0,0); -fx-text-fill: rgb(0,0,0);");

        Label input = new Label("Enter a username to start:");
        input.relocate(700*4.5/14 + 15,475*2.8/7.1);
        input.setStyle("-fx-font: 22 arial; -fx-base: rgb(170,0,0); -fx-text-fill: rgb(0,0,0);");

        usernameTextField = new TextField();
        usernameTextField.relocate(700*3.9/14 + 17 + 15,475*3.6/7.1);
        usernameTextField.setStyle("-fx-font: 22 arial; -fx-text-fill: rgb(0,0,0);");
        usernameTextField.setPrefHeight(475*0.8/7.1);

        joinChatButton = new Button("Join Chat");
        joinChatButton.relocate(500*9.9/14+700.0/4-50,475*5.5/7.1);
        joinChatButton.setPrefSize(700*2.9/14,475*0.8/7.1);
        joinChatButton.setStyle("-fx-font: 25 arial;");
        joinChatButton.setDisable(true);

        getChildren().add(title);
        getChildren().add(input);
        getChildren().add(usernameTextField);
        getChildren().add(joinChatButton);
    }

    public Button getJoinChatButton() {
        return joinChatButton;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }
}
