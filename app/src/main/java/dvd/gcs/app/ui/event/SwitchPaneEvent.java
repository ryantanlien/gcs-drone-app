package dvd.gcs.app.ui.event;

import dvd.gcs.app.ui.api.CustomEvent;
import javafx.event.EventType;

public class SwitchPaneEvent extends CustomEvent {

    public static final EventType<SwitchPaneEvent> SWITCH_PANE_EVENT_EVENT_TYPE =
            new EventType<>("SwitchPaneEvent");

    public SwitchPaneEvent() {
        super(SWITCH_PANE_EVENT_EVENT_TYPE);
    }
}
