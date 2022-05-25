package dvd.gcs.app.ui;

import dvd.gcs.app.ui.components.UiMainWindow;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * An event emitted when the stage is ready. This class extends the Spring Application Event, and is thus
 * considered under the event framework of Spring.
 *
 * It has a prototype scope thus multiple separate instances of the class can be instantiated.
 */
@Component
@Scope("prototype")
public class StageReadyEvent extends ApplicationEvent {

    /** The main window of the application. The instance is injected by Spring **/
    @Autowired
    public UiMainWindow uiMainWindow;

    /**
     * Constructs a default StageReadyEvent.
     * @param stage Stage instance created upon initialisation of the JavaFX application.
     */
    public StageReadyEvent(Stage stage) {
        super(stage);
    }

    /**
     * Gets a UiMainWindow component.
     *
     * @return a UiMainWindow instance.
     */
    public UiMainWindow getMainWindow() {
        return this.uiMainWindow;
    }
}

