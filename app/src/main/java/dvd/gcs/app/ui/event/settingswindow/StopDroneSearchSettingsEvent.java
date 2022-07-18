package dvd.gcs.app.ui.event.settingswindow;

import org.springframework.context.ApplicationEvent;

public class StopDroneSearchSettingsEvent extends ApplicationEvent {

    public StopDroneSearchSettingsEvent(Object source) {
        super(source);
    }
}
