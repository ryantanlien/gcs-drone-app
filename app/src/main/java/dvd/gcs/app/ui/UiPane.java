package dvd.gcs.app.ui;

import javafx.scene.layout.Pane;

public abstract class UiPane extends UiElement<Pane> {
    public UiPane(String fxmlFileName, Pane root) {
        super(fxmlFileName, root);
    }
}
