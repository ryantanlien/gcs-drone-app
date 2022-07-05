package dvd.gcs.app.ui.events.settings;

import dvd.gcs.app.ui.api.CustomEvent;
import dvd.gcs.app.ui.event.SwitchPaneEvent;
import javafx.event.EventType;

public class StopDroneSearchEvent extends CustomEvent {

    public static final EventType<SwitchPaneEvent> STOP_DRONE_SEARCH_EVENT_EVENT_TYPE =
            new EventType<>("StopDroneSearchEvent");

    public StopDroneSearchEvent() {
        super(STOP_DRONE_SEARCH_EVENT_EVENT_TYPE);
    }
}
