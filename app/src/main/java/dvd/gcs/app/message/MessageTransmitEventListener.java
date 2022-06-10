package dvd.gcs.app.message;

import java.util.EventListener;

public interface MessageTransmitEventListener<T> extends EventListener {
    void handleEvent(MessageTransmitEvent<T> event);
}
