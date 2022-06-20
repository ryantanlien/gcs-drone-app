package dvd.gcs.app.message;

public class DroneTelemetryMessage extends DroneMessage implements
        MessageVisitable<DroneMessageService.DroneMessageProcessor> {
    public DroneTelemetryMessage(DroneJson data) {
        super(data);
    }

    @Override
    public void accept(DroneMessageService.DroneMessageProcessor processor) {
        processor.process(this);
    }
}
