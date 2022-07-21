package dvd.gcs.app.ui.event.settingswindow;

import org.springframework.context.ApplicationEvent;

public class OkaySettingsEvent extends ApplicationEvent {

    public OkaySettingsEvent(Object source) {
        super(source);
    }
}
