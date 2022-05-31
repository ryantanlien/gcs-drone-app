package dvd.gcs.app.ui.api;

import javafx.scene.layout.Pane;

public abstract class UiSwappableLayeredPane extends UiLayeredPane implements Swappable<UiPane>{

    public UiSwappableLayeredPane(UiPane uiPane, Pane root) {
        super(uiPane, root);
    }

    public UiSwappableLayeredPane(UiPane uiPane, String fxmlFileName, Pane root) {
        super(uiPane, fxmlFileName, root);
    }
}
