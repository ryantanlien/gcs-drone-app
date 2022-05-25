package dvd.gcs.app.ui.api;

import javafx.scene.control.Button;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * A wrapper class around a JavaFX Button, to provide greater extensibility and separation of concerns in UI classes.
 * Styling is provided via a FXML file.
 *
 * This class is annotated with lazy as other components must be registered into the Spring context during runtime
 * before this component is initialized.
 */
@Component
@Lazy
public abstract class UiButton extends UiElement<Button> {

    /** The associated FXML file with this component **/
    private static final String FXML = "UiButton.fxml";

    /**
     * Constructs a default UiButton, where the it's wrapped Button are injected
     * as dependencies by Spring via constructor dependency injection.
     */
    public UiButton(Button button) {
        super(FXML, button);
    }
}