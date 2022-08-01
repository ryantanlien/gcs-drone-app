package dvd.gcs.app.event;

import dvd.gcs.app.message.DroneCommandReplyMessage;
import org.springframework.context.ApplicationEvent;

public abstract class CommandReplyEvent extends ApplicationEvent {
    public CommandReplyEvent(Object source) {
        super(source);
    }

    public abstract DroneCommandReplyMessage.CommandStatus getCommandStatus();
}
