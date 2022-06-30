package dvd.gcs.app.message;

import java.util.EventListener;

public interface MessageTransmitEventListener<U extends Message<?>> extends EventListener {
    void receiveEvent(MessageTransmitEvent<U> event);
}
