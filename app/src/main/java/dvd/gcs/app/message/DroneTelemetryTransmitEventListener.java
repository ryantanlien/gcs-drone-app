package dvd.gcs.app.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DroneTelemetryTransmitEventListener implements
        MessageTransmitEventListener<DroneTelemetryMessage> {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void receiveEvent(MessageTransmitEvent<DroneTelemetryMessage> event) {
        System.out.println("Message Received");
        publishMessageReceivedEvent(event.getMessage());
    }

    private void publishMessageReceivedEvent(DroneTelemetryMessage message) {
        MessageReceivedEvent<DroneTelemetryMessage> messageReceivedEvent = new MessageReceivedEvent<>(this, message);
        applicationEventPublisher.publishEvent(messageReceivedEvent);
    }
}
