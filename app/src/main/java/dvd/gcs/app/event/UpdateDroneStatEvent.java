package dvd.gcs.app.event;

import org.springframework.context.ApplicationEvent;

public class UpdateDroneStatEvent extends ApplicationEvent {

    private final Integer batteryPercent;
    private final Double altitude;
    private final Double velocity;
    private final Double longitude;
    private final Double latitude;

    public UpdateDroneStatEvent(Object source,
                                Integer batteryPercent,
                                Double altitude,
                                Double velocity,
                                Double longitude,
                                Double latitude) {
        super(source);
        this.batteryPercent = batteryPercent;
        this.altitude = altitude;
        this.velocity = velocity;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Integer getBatteryPercent() {
        return batteryPercent;
    }

    public Double getAltitude() {
        return altitude;
    }

    public Double getVelocity() {
        return velocity;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}
