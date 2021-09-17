import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Klausur extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        primaryStage.setScene(new Scene(borderPane, 300, 300));
        primaryStage.setTitle("Klausur");
        Button button = new Button("Switch");

        button.setOnMouseClicked(event -> {
            primaryStage.setTitle("Übung");
        });

        Label label = new Label("Java FX");
        borderPane.setRight(label);
        borderPane.setLeft(button);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }
}