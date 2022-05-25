package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiLayeredPane;
import dvd.gcs.app.ui.api.UiPane;
import dvd.gcs.app.ui.events.SwitchPaneEvent;
import dvd.gcs.app.ui.events.SwitchPaneEventHandler;
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
public class UiVideoTab extends UiLayeredPane {

    @Autowired
    public UiVideoTab(
            @Qualifier("UiVideoInterfacePanel") UiPane uiPane,
            @Qualifier("StackPane") Pane pane) {
        super(uiPane, pane);
        fillInnerParts();
        registerEventHandler(new SwitchPaneEventHandler());
    }

    private void registerEventHandler(EventHandler<? super SwitchPaneEvent> eventHandler) {
        this.getRoot().addEventHandler(SwitchPaneEvent.SWITCH_PANE_EVENT_EVENT_TYPE, eventHandler);
    }

    private void fillInnerParts() {
        super.addInnerPane();
    }
}
