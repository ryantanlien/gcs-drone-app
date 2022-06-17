package dvd.gcs.app.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DroneTransmitEventListener implements
        MessageTransmitEventListener<DroneMessage> {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void receiveEvent(MessageTransmitEvent<DroneMessage> event) {
        System.out.println("Message Received");
        publishMessageReceivedEvent(event.getMessage());
    }

    private void publishMessageReceivedEvent(DroneMessage message) {
        MessageReceivedEvent<DroneMessage> messageReceivedEvent = new MessageReceivedEvent<>(this, message);
        applicationEventPublisher.publishEvent(messageReceivedEvent);
    }
}
