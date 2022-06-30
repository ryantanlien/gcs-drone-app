package dvd.gcs.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Drone {

    //One-time data
    private final String droneModel;
    private final String droneCallSign;
    private final Boolean droneConnection;

    //Telemetry data
    private final Integer batteryPercent;
    private final Double altitude;
    private final Double velocity;
    private final Double longitude;
    private final Double latitude;

    //Add drone settings data
    private final Double geoFenceRadius;

    public Drone(
            @JsonProperty("droneModel") String droneModel,
            @JsonProperty("droneConnection") Boolean droneConnection,
            @JsonProperty("droneCallSign") String droneCallSign,
            @JsonProperty("altitude") Double altitude,
            @JsonProperty("velocity") Double velocity,
            @JsonProperty("batterPercent") Integer batteryPercent,
            @JsonProperty("longitude") Double longitude,
            @JsonProperty("latitude") Double latitude,
            @JsonProperty("geoFenceRadius") Double geoFenceRadius) {
        this.droneModel = droneModel;
        this.droneCallSign = droneCallSign;
        this.droneConnection = droneConnection;
        this.altitude = altitude;
        this.velocity = velocity;
        this.batteryPercent = batteryPercent;
        this.longitude = longitude;
        this.latitude = latitude;
        this.geoFenceRadius = geoFenceRadius;
    }

    public String getDroneModel() {
        return this.droneModel;
    }

    public String getDroneCallSign() {
        return this.droneCallSign;
    }

    public Boolean getDroneConnection() {
        return this.droneConnection;
    }

    public Integer getBatteryPercent() {
        return this.batteryPercent;
    }

    public Double getAltitude() {
        return this.altitude;
    }

    public Double getVelocity() {
        return this.velocity;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getGeoFenceRadius() {
        return this.geoFenceRadius;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Drone Callsign:");
        stringBuilder.append(this.getDroneCallSign());
        stringBuilder.append(" | ");
        stringBuilder.append("Drone Model:");
        stringBuilder.append(this.getDroneModel());
        stringBuilder.append(" | ");
        stringBuilder.append("Drone Connection:");
        stringBuilder.append(this.getDroneConnection().toString());
        stringBuilder.append(" | ");
        stringBuilder.append("Altitude:");
        stringBuilder.append(this.getAltitude().toString());
        stringBuilder.append(" | ");
        stringBuilder.append("Velocity:");
        stringBuilder.append(this.getVelocity().toString());
        stringBuilder.append(" | ");
        stringBuilder.append("Battery Percent:");
        stringBuilder.append(this.getBatteryPercent().toString());
        stringBuilder.append(" | ");
        stringBuilder.append("Longitude:");
        stringBuilder.append(this.getLongitude().toString());
        stringBuilder.append(" | ");
        stringBuilder.append("Latitude:");
        stringBuilder.append(this.getLatitude().toString());
        stringBuilder.append(" | ");
        stringBuilder.append("Geofence Radius:");
        stringBuilder.append(this.getGeoFenceRadius().toString());
        return stringBuilder.toString();
    }
}
