package dvd.gcs.app.ui.api;

import javafx.scene.layout.Pane;

public abstract class UiLayeredPane extends UiPane {

    protected UiPane uiPane;

    public UiLayeredPane(UiPane uiPane, Pane root) {
        super(root);
        this.uiPane = uiPane;
    }

    public UiLayeredPane(UiPane uiPane, String fxmlFileName, Pane root) {
        super(fxmlFileName, root);
        this.uiPane = uiPane;
    }

    protected void addInnerPane() {
        this.getRoot().getChildren().add(uiPane.getRoot());
    }
}
