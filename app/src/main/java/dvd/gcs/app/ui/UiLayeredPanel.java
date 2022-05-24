package dvd.gcs.app.ui;

import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Scope("prototype")
public class UiLayeredPanel extends UiPane {
    private static final String FXML = "UiLayeredPanel.fxml";

    /**
     * Constituent UiPane
     **/
    UiPane uiPane;

    /** Constituent UiMenuBar component **/
    UiMenuBar uiMenuBar;

    /**
     * Constructs a default UiBasePanel, where the wrapped JavaFX Pane is injected
     * as a dependency by Spring via constructor dependency injection.
     *
     * @param pane the wrapped around pane obtained through dependency injection.
     */
    @Autowired
    public UiLayeredPanel(
            @Qualifier("VBox") Pane pane,
            UiMenuBar uiMenuBar,
            @Qualifier("UiBasePanel") UiPane uiPane) {
        super(FXML, pane);
        this.uiPane = uiPane;
        this.uiMenuBar = uiMenuBar;
        fillInnerParts();
    }

    /**
     * Fills the JavaFX placeholders.
     */
    private void fillInnerParts() {
        this.getRoot().getChildren().add(uiMenuBar.getRoot());
        this.getRoot().getChildren().add(uiPane.getRoot());
    }
}
