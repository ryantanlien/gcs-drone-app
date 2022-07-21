package dvd.gcs.app.ui.event.settingswindow;

import org.springframework.context.ApplicationEvent;

public class UpdateGeofenceRadiusSettingsEvent extends ApplicationEvent {

    private final double geofenceRadius;

    public UpdateGeofenceRadiusSettingsEvent(Object source, double geofenceRadius) {
        super(source);
        this.geofenceRadius = geofenceRadius;
    }

    public double getGeofenceRadius() {
        return geofenceRadius;
    }
}
