package dvd.gcs.app.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiMainWindow extends UiElement<Stage> {

    private static final String FXML = "UiMainWindow.fxml";
    private Stage stage;
    UiBasePanel uiBasePanel;


    @Autowired
    public UiMainWindow(Stage stage, UiBasePanel uiBasePanel) {
        super(FXML, stage);
        this.stage = stage;
        this.uiBasePanel = uiBasePanel;
        stage.setScene(new Scene(uiBasePanel.getRoot()));
    }

    public void show() {
        this.stage.show();
    }
}
