package dvd.gcs.app.ui.components;

import dvd.gcs.app.ui.api.UiLayeredPane;
import dvd.gcs.app.ui.api.UiPane;
import dvd.gcs.app.ui.api.UiSwappableLayeredPane;
import dvd.gcs.app.ui.event.SwitchPaneEvent;
import dvd.gcs.app.ui.event.SwitchPaneEventHandler;
import javafx.event.EventHandler;
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

    /** Constituent UiPane component **/
    UiPane uiPane;

    /**
     * Constructs a default UiLayeredPanel, where the wrapped JavaFX Pane is injected
     * as a dependency by Spring via constructor dependency injection.
     *
     * @param pane the wrapped around pane obtained through dependency injection.
     */
    @Autowired
    public UiLayeredPanel (
            @Qualifier("VBox") Pane pane,
            @Qualifier("UiVideoTab") UiSwappableLayeredPane uiSwappableLayeredPane,
            UiMenuBar uiMenuBar) {
        super(uiSwappableLayeredPane, pane);
        this.uiMenuBar = uiMenuBar;
        this.uiPane = uiSwappableLayeredPane;
        fillInnerParts();
        registerEventHandler(
                new SwitchPaneEventHandler(
                        this, uiSwappableLayeredPane));
    }


    /**
     * Fills the JavaFX placeholders.
     */
    private void fillInnerParts() {
        this.getRoot().getChildren().add(uiMenuBar.getRoot());
        super.addInnerPane();
    }

    private void registerEventHandler(EventHandler<? super SwitchPaneEvent> eventHandler) {
        this.getRoot().addEventHandler(SwitchPaneEvent.SWITCH_PANE_EVENT_EVENT_TYPE, eventHandler);
    }
}
