package dvd.gcs.app.event;

import dvd.gcs.app.message.DroneCommandReplyMessage;

public class StartLandingReplyEvent extends CommandReplyEvent {

    private final DroneCommandReplyMessage.CommandStatus commandStatus;

    public StartLandingReplyEvent(Object source, DroneCommandReplyMessage.CommandStatus commandStatus) {
        super(source);
        this.commandStatus = commandStatus;
    }

    @Override
    public DroneCommandReplyMessage.CommandStatus getCommandStatus() {
        return this.commandStatus;
    }
}
