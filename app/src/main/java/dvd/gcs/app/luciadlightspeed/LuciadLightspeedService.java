package dvd.gcs.app.luciadlightspeed;

import dvd.gcs.app.message.DroneCommandMessage;
import dvd.gcs.app.message.DroneJson;
import dvd.gcs.app.message.MessageDispatchEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("Singleton")
public class LuciadLightspeedService {
    @Autowired
    @Qualifier("LuciadLightspeedMap")
    LuciadMapInterface luciadLightspeedMap; // TODO: may not work

    @Autowired
    ApplicationEventPublisher applicationEventPublisher; // Springboot event publisher

    public void example() { // example
        MessageDispatchEvent<DroneCommandMessage> messageDispatchEvent = new MessageDispatchEvent<DroneCommandMessage>(
                this, new DroneCommandMessage(
                        new DroneJson(
                                "PLACEHOLDER", DroneJson.Type.COMMAND), DroneCommandMessage.CommandType.SET_ALTITUDE));
        applicationEventPublisher.publishEvent(messageDispatchEvent);
        // given to a class that extends ApplicationListener<DroneCommandMessage>
        // and overrides onApplicationEvent(MessageDispatchEvent<DroneCommandMessage> event)
    }
}
