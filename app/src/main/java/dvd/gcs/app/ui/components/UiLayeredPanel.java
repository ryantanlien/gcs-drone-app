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
@Scope("singleton")
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
            @Qualifier("UiMapBasePane") UiSwappableLayeredPane uiOtherSwappableLayeredPane,
            UiMenuBar uiMenuBar) {
        super(uiOtherSwappableLayeredPane, pane);
        this.uiMenuBar = uiMenuBar;
        this.uiPane = uiSwappableLayeredPane;

        //Configure UiVideoTab resizing behavior, need to set the borderPane to occupy the rest of the VBox in UiLayeredPanel
        uiSwappableLayeredPane.getRoot().prefHeightProperty().bind(this.getRoot().heightProperty());
        uiSwappableLayeredPane.getRoot().prefWidthProperty().bind(this.getRoot().widthProperty());

        fillInnerParts();
        registerEventHandler(
                new SwitchPaneEventHandler(
                        this,  uiOtherSwappableLayeredPane, uiSwappableLayeredPane));
    }

    /**
     * Fills the JavaFX placeholders.
     */
    private void fillInnerParts() {
        super.addInnerPane();
    }

    private void registerEventHandler(EventHandler<? super SwitchPaneEvent> eventHandler) {
        this.getRoot().addEventHandler(SwitchPaneEvent.SWITCH_PANE_EVENT_EVENT_TYPE, eventHandler);
    }
}
