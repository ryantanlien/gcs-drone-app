package dvd.gcs.app.luciadlightspeed.event;

public interface MessageDispatchEventPublisher {
    public void publish(); // uses application event publisher to publish the event stored inside
}
