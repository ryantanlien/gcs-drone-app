package sg.gov.dsta.thickdemo.ui;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UiMainWindow extends UiElement<Stage> {

    private static final String FXML = "UiMainWindow.fxml";
    private Stage stage;

    public UiMainWindow(Stage stage) {
        super(FXML, stage);
        this.stage = stage;
        GridPane gridPane = new GridPane();
        stage.setScene(new Scene(new UiBasePanel(gridPane).getRoot()));
    }

    public void show() {
        this.stage.show();
    }
}
