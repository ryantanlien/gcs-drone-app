package dvd.gcs.app.message;

import java.util.Collection;
import java.util.EventObject;

public class MessageTransmitEvent<U extends Message<?>> extends EventObject {

    private final U message;

    /**
     * Constructs a MessageReceivedEvent.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public MessageTransmitEvent(Object source, U message) {
        super(source);
        this.message = message;
    }

    public U getMessage() {
        return this.message;
    }
}
