package dvd.gcs.app.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * A wrapper class around a JavaFX Stage, to provide greater extensibility and separation of concerns in UI classes.
 * Styling is provided via a FXML file.
 *
 * This class is annotated with lazy as other components must be registered into the Spring context during runtime
 * before this component is initialized.
 */
@Component
@Lazy
public class UiMainWindow extends UiElement<Stage> {

    /** The associated FXML file with this component **/
    private static final String FXML = "UiMainWindow.fxml";

    /** Constituent UiBasePanel component **/
    UiBasePanel uiBasePanel;

    /**
     * Constructs a default UiMainWindow, where the wrapped Stage and constituent UiBasePanel are injected
     * as dependencies by Spring via constructor dependency injection.
     *
     * @param stage the stage registered as a dependency created upon initialisation of the JavaFX application.
     * @param uiBasePanel the constituent UiBasePanel instance
     */
    @Autowired
    public UiMainWindow(Stage stage, UiBasePanel uiBasePanel) {
        super(FXML, stage);
        this.uiBasePanel = uiBasePanel;
        Scene scene = new Scene(uiBasePanel.getRoot());
        scene.getStylesheets().add("stylesheets/AppStylesheet.css");
        stage.setScene(scene);
    }

    /**
     * Displays the JavaFX window.
     */
    public void show() {
        this.getRoot().show();
    }
}
