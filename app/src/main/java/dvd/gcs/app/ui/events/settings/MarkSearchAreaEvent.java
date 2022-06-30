package dvd.gcs.app.ui.events.settings;

import dvd.gcs.app.ui.api.CustomEvent;
import dvd.gcs.app.ui.events.SwitchPaneEvent;
import javafx.event.EventType;

public class MarkSearchAreaEvent extends CustomEvent {

    public static final EventType<SwitchPaneEvent> MARK_SEARCH_AREA_EVENT_EVENT_TYPE =
            new EventType<>("MarkSearchAreaEvent");

    public MarkSearchAreaEvent() {
        super(MARK_SEARCH_AREA_EVENT_EVENT_TYPE);
    }
}
