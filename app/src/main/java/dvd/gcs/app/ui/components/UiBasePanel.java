package dvd.gcs.app.ui.components;

import dvd.gcs.app.ui.api.UiButton;
import dvd.gcs.app.ui.api.UiPane;

import javafx.scene.layout.Pane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("UiBasePanel")
@Lazy
@Scope("prototype")
public class UiBasePanel extends UiPane {

    /** Constituent UiButton component **/
    UiButton uiButton;

    /** Constituent UiDroneFeedWindow component **/
    UiDroneFeedWindow uiDroneFeedWindow;

    /**
     * Constructs a default UiBasePanel, where the wrapped JavaFX Pane is injected
     * as a dependency by Spring via constructor dependency injection.
     *
     * @param pane the wrapped around pane obtained through dependency injection.
     * @param uiButton the wrapped around ui
     */
    @Autowired
    public UiBasePanel(
            @Qualifier("VBox") Pane pane,
            @Qualifier ("UiSimpleButton") UiButton uiButton,
            UiDroneFeedWindow uiDroneFeedWindow) {
        super(pane);
        this.uiButton = uiButton;
        this.uiDroneFeedWindow = uiDroneFeedWindow;
        fillInnerParts();
    }

    /**
     * Fills the JavaFX placeholders.
     */
    private void fillInnerParts() {
        this.getRoot().getChildren().add(uiButton.getRoot());
        this.getRoot().getChildren().add(uiDroneFeedWindow.getRoot());
    }
}
