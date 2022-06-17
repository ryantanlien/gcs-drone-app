package dvd.gcs.app.message;

public class DroneMessage extends Message<DroneJson> implements MessageVisitable<DroneMessageService.DroneMessageProcessor>{
    public DroneMessage(DroneJson data) {
        super(data);
    }

    @Override
    public void accept(DroneMessageService.DroneMessageProcessor processor) {
        processor.process(this);
    }
}
