package dvd.gcs.app.ui.event;

import dvd.gcs.app.ui.api.CustomEvent;
import dvd.gcs.app.ui.event.SwitchPaneEvent;
import javafx.event.EventType;

public class ApplySettingsEvent extends CustomEvent {

    public static final EventType<SwitchPaneEvent> APPLY_SETTINGS_EVENT_EVENT_TYPE =
            new EventType<>("ApplySettingsEvent");

    public ApplySettingsEvent() {
        super(APPLY_SETTINGS_EVENT_EVENT_TYPE);
    }
}
