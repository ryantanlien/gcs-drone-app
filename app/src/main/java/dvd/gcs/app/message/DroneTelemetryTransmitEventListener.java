package dvd.gcs.app.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Scope("prototype")
public class DroneTelemetryTransmitEventListener implements
        MessageTransmitEventListener<StringCollectionMessage> {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void receiveEvent(MessageTransmitEvent<StringCollectionMessage> event) {
        publishMessageReceivedEvent(event.getMessage());
        System.out.println("Message Received");
    }

    private void publishMessageReceivedEvent(StringCollectionMessage message) {
        MessageReceivedEvent<StringCollectionMessage> messageReceivedEvent = new MessageReceivedEvent<>(this, message);
        applicationEventPublisher.publishEvent(messageReceivedEvent);
    }
}
