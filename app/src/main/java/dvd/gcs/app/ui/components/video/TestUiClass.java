package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.message.DroneCommandMessage;
import dvd.gcs.app.message.DroneJson;
import dvd.gcs.app.message.DroneMessage;
import dvd.gcs.app.message.MessageDispatchEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class TestUiClass {

    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public TestUiClass(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
        publishEvent();
    }

    public void publishEvent() {
        DroneJson droneJson = new DroneJson(
                "{" +
                "\"droneCallSign\":\"Alpha\"," +
                "\"geoFenceRadius\":0.3" + "}", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.SET_GEOFENCE);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        applicationEventPublisher.publishEvent(messageDispatchEvent);
    }
}
