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
    private final String droneStatus;
    private final Boolean droneConnection;

    //Telemetry data
    private final Integer batteryPercent;
    private final Double altitude;
    private final Double velocity;
    private final Double longitude;
    private final Double latitude;
    private final Double homeLongitude;
    private final Double homeLatitude;

    //Add drone settings data
    private final Double geoFenceRadius;
    private final Double maxAltitude;
    private final Double maxVelocity;

    public Drone(
            @JsonProperty("droneModel") String droneModel,
            @JsonProperty("droneConnection") Boolean droneConnection,
            @JsonProperty("droneCallSign") String droneCallSign,
            @JsonProperty("droneStatus") String droneStatus,
            @JsonProperty("altitude") Double altitude,
            @JsonProperty("velocity") Double velocity,
            @JsonProperty("batterPercent") Integer batteryPercent,
            @JsonProperty("longitude") Double longitude,
            @JsonProperty("latitude") Double latitude,
            @JsonProperty("geoFenceRadius") Double geoFenceRadius,
            @JsonProperty("maxAltitude") Double maxAltitude,
            @JsonProperty("maxVelocity") Double maxVelocity,
            @JsonProperty("homeLongitude") Double homeLongitude,
            @JsonProperty("homeLatitude") Double homeLatitude) {
        this.droneModel = droneModel;
        this.droneCallSign = droneCallSign;
        this.droneStatus = droneStatus;
        this.droneConnection = droneConnection;
        this.altitude = altitude;
        this.velocity = velocity;
        this.batteryPercent = batteryPercent;
        this.longitude = longitude;
        this.latitude = latitude;
        this.geoFenceRadius = geoFenceRadius;
        this.maxAltitude = maxAltitude;
        this.maxVelocity = maxVelocity;
        this.homeLongitude = homeLongitude;
        this.homeLatitude = homeLatitude;
    }

    public String getDroneModel() {
        return this.droneModel;
    }

    public String getDroneCallSign() {
        return this.droneCallSign;
    }
    public String getDroneStatus() {
        return this.droneStatus;
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

    public Double getMaxAltitude() {
        return this.maxAltitude;
    }

    public Double getMaxVelocity() {
        return this.maxVelocity;
    }

    public Double getHomeLongitude() {
        return this.homeLongitude;
    }

    public Double getHomeLatitude() {
        return this.homeLatitude;
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
        stringBuilder.append(("Drone Status:"));
        stringBuilder.append(this.getDroneStatus());
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
        stringBuilder.append(" | ");
        stringBuilder.append("Max Altitude:");
        stringBuilder.append(this.getMaxAltitude().toString());
        stringBuilder.append(" | ");
        stringBuilder.append("Max Velocity:");
        stringBuilder.append(this.getMaxVelocity().toString());
        stringBuilder.append(" | ");
        stringBuilder.append("Home Longitude:");
        stringBuilder.append(this.getHomeLongitude().toString());
        stringBuilder.append(" | ");
        stringBuilder.append("Home Latitude:");
        stringBuilder.append(this.getHomeLatitude().toString());
        return stringBuilder.toString();
    }
}
