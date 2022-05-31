package dvd.gcs.app.ui.api;

import javafx.scene.layout.Pane;

public abstract class UiPane extends UiElement<Pane> {

    private static final String FXML = "UiPane.fxml";

    public UiPane(Pane root) {
        super(FXML, root);
    }

    /** Overloaded method for extending object to define it's own FXML file instead of using the default **/
    public UiPane(String fxmlFileName, Pane root) {
        super(fxmlFileName, root);
    }
}
