package dvd.gcs.app.ui.event;

import dvd.gcs.app.ui.api.CustomEvent;
import dvd.gcs.app.ui.event.SwitchPaneEvent;
import javafx.event.EventType;

public class CancelSettingsEvent extends CustomEvent {

    public static final EventType<SwitchPaneEvent> CANCEL_SETTINGS_EVENT_EVENT_TYPE =
            new EventType<>("CancelSettingsEvent");

    public CancelSettingsEvent() {
        super(CANCEL_SETTINGS_EVENT_EVENT_TYPE);
    }
}