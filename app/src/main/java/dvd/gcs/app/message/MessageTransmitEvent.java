package dvd.gcs.app.message;

import java.util.Collection;
import java.util.EventObject;

public class MessageTransmitEvent<T> extends EventObject {

    private final Collection<T> data;

    /**
     * Constructs a MessageReceivedEvent.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public MessageTransmitEvent(Object source, Collection<T> list) {
        super(source);
        this.data = list;
    }

    public Collection<T> getData() {
        return this.data;
    }
}
