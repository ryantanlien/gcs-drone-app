package dvd.gcs.app.event;

import org.springframework.context.ApplicationEvent;

public class UpdateDroneSettingsEvent extends ApplicationEvent {

    private final Double geoFenceRadius;
    private final Double maxVelocity;
    private final Double maxAltitude;

    public UpdateDroneSettingsEvent(Object source, Double geoFenceRadius, Double maxVelocity, Double maxAltitude) {
        super(source);
        this.geoFenceRadius = geoFenceRadius;
        this.maxVelocity = maxVelocity;
        this.maxAltitude = maxAltitude;
    }

    public Double getGeoFenceRadius() {
        return this.geoFenceRadius;
    }

    public Double getMaxVelocity() {
        return this.maxVelocity;
    }

    public Double getMaxAltitude() {
        return this.maxAltitude;
    }

}
