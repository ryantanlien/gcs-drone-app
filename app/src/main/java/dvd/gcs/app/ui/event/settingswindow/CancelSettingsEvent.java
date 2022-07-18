package dvd.gcs.app.ui.event.settingswindow;

import org.springframework.context.ApplicationEvent;

public class CancelSettingsEvent extends ApplicationEvent {

    public CancelSettingsEvent(Object source) {
        super(source);
    }
}
