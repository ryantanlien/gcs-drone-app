package dvd.gcs.app.message;

public class DroneTelemetryMessage extends Message<DroneJson> {
    public DroneTelemetryMessage(DroneJson data) {
        super(data);
    }
}
