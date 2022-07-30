package dvd.gcs.app.event;

import dvd.gcs.app.message.DroneCommandReplyMessage;

public class StartTakeoffReplyEvent extends CommandReplyEvent {

    private final DroneCommandReplyMessage.CommandStatus commandStatus;

    public StartTakeoffReplyEvent(Object source, DroneCommandReplyMessage.CommandStatus commandStatus) {
        super(source);
        this.commandStatus = commandStatus;
    }

    @Override
    public DroneCommandReplyMessage.CommandStatus getCommandStatus() {
        return this.commandStatus;
    }
}
