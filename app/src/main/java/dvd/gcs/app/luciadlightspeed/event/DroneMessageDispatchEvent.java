package dvd.gcs.app.luciadlightspeed.event;

import dvd.gcs.app.message.DroneMessage;
import dvd.gcs.app.message.MessageDispatchEvent;
import org.springframework.context.ApplicationEventPublisher;

public class DroneMessageDispatchEvent implements MessageDispatchEventPublisher {
    private MessageDispatchEvent<DroneMessage> eventToPublish;
    private ApplicationEventPublisher eventPublisher;

    public DroneMessageDispatchEvent(MessageDispatchEvent<DroneMessage> messageDispatchEvent, ApplicationEventPublisher eventPublisher) {
        this.eventToPublish = messageDispatchEvent;
        this.eventPublisher = eventPublisher;
    }

    public void execute() {
        eventPublisher.publishEvent(eventToPublish);
    }
}
