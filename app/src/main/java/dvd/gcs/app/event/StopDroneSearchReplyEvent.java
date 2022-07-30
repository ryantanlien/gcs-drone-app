package dvd.gcs.app.event;

import dvd.gcs.app.message.DroneCommandReplyMessage;


public class StopDroneSearchReplyEvent extends CommandReplyEvent {

    private final DroneCommandReplyMessage.CommandStatus commandStatus;

    public StopDroneSearchReplyEvent(Object source, DroneCommandReplyMessage.CommandStatus commandStatus) {
        super(source);
        this.commandStatus = commandStatus;
    }

    @Override
    public DroneCommandReplyMessage.CommandStatus getCommandStatus() {
        return this.commandStatus;
    }
}
