package dvd.gcs.app.luciadlightspeed.event;

public interface MessageDispatchEventPublisher {
    public void execute(); // uses application event publisher to publish the event stored inside
}
