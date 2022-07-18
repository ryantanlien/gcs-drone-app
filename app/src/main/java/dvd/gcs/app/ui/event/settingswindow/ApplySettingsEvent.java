package dvd.gcs.app.ui.event.settingswindow;

import org.springframework.context.ApplicationEvent;

public class ApplySettingsEvent extends ApplicationEvent {

    public ApplySettingsEvent(Object source) {
        super(source);
    }
}
