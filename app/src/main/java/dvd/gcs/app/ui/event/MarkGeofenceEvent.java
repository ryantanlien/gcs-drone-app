package dvd.gcs.app.ui.event;

import dvd.gcs.app.ui.api.CustomEvent;
import dvd.gcs.app.ui.event.SwitchPaneEvent;
import javafx.event.EventType;

public class MarkGeofenceEvent extends CustomEvent {

    public static final EventType<SwitchPaneEvent> MARK_GEOFENCE_EVENT_EVENT_TYPE =
            new EventType<>("MarkGeofenceEvent");

    public MarkGeofenceEvent() {
        super(MARK_GEOFENCE_EVENT_EVENT_TYPE);
    }
}
