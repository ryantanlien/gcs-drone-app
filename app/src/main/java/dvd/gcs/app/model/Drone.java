package dvd.gcs.app.model;

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
    private final Double batteryPercent;
    private final Double altitude;
    private final Double velocity;

    public Drone(String droneModel,
                 Boolean droneConnection,
                 String droneCallSign,
                 Double altitude,
                 Double velocity,
                 Double batteryPercent) {
        this.droneModel = droneModel;
        this.droneCallSign = droneCallSign;
        this.droneConnection = droneConnection;
        this.altitude = altitude;
        this.velocity = velocity;
        this.batteryPercent = batteryPercent;
    }

    public final String getDroneModel() {
        return this.droneModel;
    }

    public String getDroneCallSign() {
        return this.droneCallSign;
    }

    public Boolean getDroneConnection() {
        return this.droneConnection;
    }

    public Double getBatteryPercent() {
        return this.batteryPercent;
    }

    public Double getAltitude() {
        return this.altitude;
    }

    public Double getVelocity() {
        return this.velocity;
    }
}
