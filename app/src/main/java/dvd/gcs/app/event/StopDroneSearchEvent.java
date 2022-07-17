package dvd.gcs.app.event;

import dvd.gcs.app.message.DroneCommandReplyMessage;
import dvd.gcs.app.ui.api.CustomEvent;
import dvd.gcs.app.ui.event.SwitchPaneEvent;
import javafx.event.EventType;
import org.springframework.context.ApplicationEvent;

public class StopDroneSearchEvent extends ApplicationEvent {

    private final DroneCommandReplyMessage.CommandStatus commandStatus;

    public StopDroneSearchEvent(Object source, DroneCommandReplyMessage.CommandStatus commandStatus) {
        super(source);
        this.commandStatus = commandStatus;
    }

    public DroneCommandReplyMessage.CommandStatus getCommandStatus() {
        return this.commandStatus;
    }
}
