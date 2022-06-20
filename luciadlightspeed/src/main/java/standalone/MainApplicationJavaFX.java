package standalone;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApplicationJavaFX extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lightspeed demo application");
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
