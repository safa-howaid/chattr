import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class StartupViewGUI extends StackPane {
    private VBox bx;
    private Label title_label;
    private Label input_label;
    private TextField username;
    private Label errorLabel;
    private Button join_chat;
    private Client model;

    public StartupViewGUI(Client model) {
        this.model = model;
        bx = new VBox();
        bx.setAlignment(Pos.CENTER);

        title_label = new Label("Internet Relay Chat");
        title_label.relocate(700*3.5/14,475/7.1);
        title_label.setStyle("-fx-font: 42 arial; -fx-base: rgb(170,0,0); -fx-text-fill: rgb(0,0,0);");

        input_label = new Label("Enter a username to start");
        input_label.relocate(700*4.5/14 + 15,475*2.8/7.1);
        input_label.setStyle("-fx-font: 22 arial; -fx-base: rgb(170,0,0); -fx-text-fill: rgb(0,0,0);");

        username = new TextField();
        //username.relocate(700*3.9/14 + 17 + 15,475*3.6/7.1);
        //username.setStyle("-fx-font: 22 arial; -fx-base: rbg(75,4,32); -fx-text-fill: rgb(0,0,0);");
        //username.setPrefHeight(475*0.8/7.1);
        username.setMaxWidth(200);
        username.setMinHeight(475*0.8/7.1);

        errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: rgb(255,0,0);");

        join_chat = new Button("Join Chat");
        join_chat.relocate(500*9.9/14+700.0/4-50,475*5.5/7.1);
        join_chat.setPrefSize(700*2.9/14,475*0.8/7.1);
        join_chat.setStyle("-fx-font: 25 arial;");

        bx.setSpacing(15);
        bx.getChildren().addAll(title_label,input_label,username,errorLabel,join_chat);

        getChildren().add(bx);
    }

    public Button getJoin_chat() {
        return join_chat;
    }

    public Label getInput_label() {
        return input_label;
    }

    public Label getTitle_label() {
        return title_label;
    }

    public TextField getUsername() {
        return username;
    }

    public VBox getBx() {
        return bx;
    }







}
