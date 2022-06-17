package dvd.gcs.app.message;

import dvd.gcs.app.model.Drone;
import dvd.gcs.app.model.DroneJsonDeserializer;
import dvd.gcs.app.model.UpdateDroneModelEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DroneTelemetryMessageService implements ApplicationListener<MessageReceivedEvent<DroneTelemetryMessage>> {

    @Autowired
    DroneJsonDeserializer droneJsonDeserializer;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void onApplicationEvent(MessageReceivedEvent<DroneTelemetryMessage> event) {
        System.out.println(event.getMessage().getClass().toString() + " Received!");
        DroneTelemetryMessage droneTelemetryMessage = event.getMessage();
        DroneJson droneJson = droneTelemetryMessage.getData();
        Drone drone = droneJsonDeserializer.deserializeDroneJson(droneJson);
        applicationEventPublisher.publishEvent(new UpdateDroneModelEvent(this, drone));
    }
}
