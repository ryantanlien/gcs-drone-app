package dvd.gcs.app.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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
public class UiBasePanel extends UiElement<GridPane> {

    private static final String FXML = "UiBasePanel.fxml";

    /** Constituent UiButton component **/
    UiButton uiButton;

    /** A placeholder for the JavaFXML button **/
    @javafx.fxml.FXML
    private Button buttonPlaceHolder;

    /**
     * Constructs a default UiBasePanel, where the it's wrapped JavaFX GridPane and constituent UiButton are injected
     * as dependencies by Spring via constructor dependency injection.
     *
     * @param gridPane the wrapped around button obtained through dependency injection.
     * @param uiButton the constituent UiButton.
     */
    @Autowired
    public UiBasePanel(GridPane gridPane, UiButton uiButton) {
        super(FXML, gridPane);
        this.uiButton = uiButton;
        fillInnerParts();
    }

    /**
     * Fills the JavaFX button placeholder.
     */
    void fillInnerParts() {
        this.getRoot().getChildren().add(uiButton.getRoot());
    }
}
