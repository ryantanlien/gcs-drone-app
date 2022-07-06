package dvd.gcs.app.ui.events.settings;

import dvd.gcs.app.ui.api.CustomEvent;
import dvd.gcs.app.ui.event.SwitchPaneEvent;
import javafx.event.EventType;

public class MarkHomeEvent extends CustomEvent {

    public static final EventType<SwitchPaneEvent> MARK_HOME_EVENT_EVENT_TYPE =
            new EventType<>("MarkHomeEvent");

    public MarkHomeEvent() {
        super(MARK_HOME_EVENT_EVENT_TYPE);
    }
}
