package dvd.gcs.app.ui;

import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * A wrapper class around a JavaFX GridPane, to provide greater extensibility and separation of concerns in UI classes.
 * Styling is provided via a FXML file.
 *
 * This class is annotated with lazy as other components must be registered into the Spring context during runtime
 * before this component is initialized.
 */
@Component
@Lazy
public class UiBasePanel extends UiElement<VBox> {

    private static final String FXML = "UiBasePanel.fxml";

    /** Constituent UiButton component **/
    UiButton uiButton;

    /** Constituent UiMenuBar component **/
    UiMenuBar uiResizableMenuBar;

    /** Constituent UiDroneFeedWindow component **/
    UiDroneFeedWindow uiDroneFeedWindow;

    /** A placeholder for the JavaFXML button **/
    @javafx.fxml.FXML
    private Button buttonPlaceHolder;

    /** A placeholder for the JavaFXML menuBar **/
    @javafx.fxml.FXML
    private MenuBar menuBarPlaceHolder;

    /** A placeholder for the JavaFXML droneFeedWindow **/
    @javafx.fxml.FXML
    private UiDroneFeedWindow droneFeedWindowPlaceHolder;

    /**
     * Constructs a default UiBasePanel, where the wrapped JavaFX GridPane and constituent UiButton are injected
     * as dependencies by Spring via constructor dependency injection.
     *
     * @param vBox the wrapped around VBox obtained through dependency injection.
     * @param uiButton the constituent UiButton.
     */
    @Autowired
    public UiBasePanel(
            VBox vBox,
            UiButton uiButton,
            UiMenuBar uiResizableMenuBar,
            UiDroneFeedWindow uiDroneFeedWindow) {
        super(FXML, vBox);
        this.uiButton = uiButton;
        this.uiResizableMenuBar = uiResizableMenuBar;
        this.uiDroneFeedWindow = uiDroneFeedWindow;
        fillInnerParts();
    }

    /**
     * Fills the JavaFX placeholders.
     */
    private void fillInnerParts() {
        this.getRoot().getChildren().add(uiButton.getRoot());
        this.getRoot().getChildren().add(uiResizableMenuBar.getRoot());
        this.getRoot().getChildren().add(uiDroneFeedWindow.getRoot());
    }
}
