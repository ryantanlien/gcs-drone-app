package dvd.gcs.app.message;

import java.util.Collection;
import java.util.EventObject;

public class MessageTransmitEvent<T> extends EventObject {

    private final Collection<T> message;

    /**
     * Constructs a MessageReceivedEvent.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public MessageTransmitEvent(Object source, Collection<T> message) {
        super(source);
        this.message = message;
    }

    public Collection<T> getMessage() {
        return this.message;
    }
}
