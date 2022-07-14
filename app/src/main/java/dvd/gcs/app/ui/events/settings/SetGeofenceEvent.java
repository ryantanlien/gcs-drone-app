package dvd.gcs.app.ui.events.settings;

import dvd.gcs.app.message.DroneCommandReplyMessage;
import org.springframework.context.ApplicationEvent;

public class SetGeofenceEvent extends ApplicationEvent {

    private final DroneCommandReplyMessage.CommandStatus commandStatus;

    public SetGeofenceEvent(Object source, DroneCommandReplyMessage.CommandStatus commandStatus) {
        super(source);
        this.commandStatus = commandStatus;
    }

    public DroneCommandReplyMessage.CommandStatus getCommandStatus() {
        return this.commandStatus;
    }
}
