package dvd.gcs.app.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Scope("prototype")
public class DroneTelemetryTransmitEventListener implements MessageTransmitEventListener<String> {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void receiveEvent(MessageTransmitEvent<String> event) {
        publishMessageReceivedEvent(event.getMessage());
        System.out.println("Message Received");
    }

    private void publishMessageReceivedEvent(Collection<String> message) {
        MessageReceivedEvent<String> messageReceivedEvent = new MessageReceivedEvent<>(this, message);
        applicationEventPublisher.publishEvent(messageReceivedEvent);
    }
}
