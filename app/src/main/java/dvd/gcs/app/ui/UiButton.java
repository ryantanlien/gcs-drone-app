package dvd.gcs.app.ui;

import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UiButton extends UiElement<Button> {

    /** The associated FXML file with this component **/
    private static final String FXML = "UiButton.fxml";

    /** Text of the Button **/
    private String text;

    /**
     * Constructs a default UiButton, where the it's wrapped Button are injected
     * as dependencies by Spring via constructor dependency injection.
     *
     * @param button the wrapped around button obtained through dependency injection.
     */
    @Autowired
    public UiButton(Button button) {
        super(FXML, button);
        this.text = "Button";
        Button root = this.getRoot();
        root.setText(this.text);
        root.setOnAction(e -> {
            root.setText("Button Clicked");
        });
    }
}