package dvd.gcs.app.event;

import dvd.gcs.app.message.DroneCommandReplyMessage;
import org.springframework.context.ApplicationEvent;

public class UploadDroneMissionEvent extends ApplicationEvent {
    private final DroneCommandReplyMessage.CommandStatus commandStatus;

    public UploadDroneMissionEvent(Object source, DroneCommandReplyMessage.CommandStatus commandStatus) {
        super(source);
        this.commandStatus = commandStatus;
    }

    public DroneCommandReplyMessage.CommandStatus getCommandStatus() {
        return this.commandStatus;
    }
}
