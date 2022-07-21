package dvd.gcs.app.luciadlightspeed.event;

import dvd.gcs.app.message.DroneMessage;
import dvd.gcs.app.message.MessageDispatchEvent;
import org.springframework.context.ApplicationEventPublisher;

public class QueueMessageDispatchEvent implements MessageDispatchEventPublisher {
    private final MessageDispatchEvent<DroneMessage> eventToPublish;
    private final ApplicationEventPublisher eventPublisher;

    public QueueMessageDispatchEvent(MessageDispatchEvent<DroneMessage> messageDispatchEvent, ApplicationEventPublisher eventPublisher) {
        this.eventToPublish = messageDispatchEvent;
        this.eventPublisher = eventPublisher;
    }

    public void publish() {
        eventPublisher.publishEvent(eventToPublish);
    }
}
