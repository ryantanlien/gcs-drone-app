package dvd.gcs.app.ui.components;

import dvd.gcs.app.ui.api.UiLayeredPane;
import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Scope("prototype")
public class UiLayeredPanel extends UiLayeredPane {

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
            @Qualifier("UiBasePanel") UiPane uiPane,
            UiMenuBar uiMenuBar) {
        super(uiPane, pane);
        this.uiMenuBar = uiMenuBar;
        fillInnerParts();
    }

    /**
     * Fills the JavaFX placeholders.
     */
    private void fillInnerParts() {
        this.getRoot().getChildren().add(uiMenuBar.getRoot());
        super.addInnerPane();
    }
}
