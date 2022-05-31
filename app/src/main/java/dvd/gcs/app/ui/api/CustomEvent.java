package dvd.gcs.app.ui.api;

import javafx.event.Event;
import javafx.event.EventType;

public abstract class CustomEvent extends Event {

    public static final EventType<CustomEvent> CUSTOM_EVENT_EVENT_TYPE =
            new EventType<>("CustomEvent");

    public CustomEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
