package dvd.gcs.app.message;

import dvd.gcs.app.model.Drone;
import dvd.gcs.app.model.DroneJsonDeserializer;
import dvd.gcs.app.event.UpdateDroneModelEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DroneMessageService implements ApplicationListener<MessageReceivedEvent<? extends DroneMessage>> {

    @Autowired
    DroneJsonDeserializer droneJsonDeserializer;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    DroneMessageProcessor droneMessageProcessor = new DroneMessageProcessor();

    private interface MessageProcessor {
        void process(DroneMessage droneMessage);
        void process(DroneTelemetryMessage droneTelemetryMessage);
    }

    public class DroneMessageProcessor implements MessageProcessor {

        @Override
        public void process(DroneTelemetryMessage droneTelemetryMessage) {
            DroneJson droneJson = droneTelemetryMessage.getData();
            Drone drone = droneJsonDeserializer.deserializeDroneJson(droneJson);
            applicationEventPublisher.publishEvent(new UpdateDroneModelEvent(this, drone));
        }

        @Override
        public void process(DroneMessage droneMessage) {
            DroneJson droneJson = droneMessage.getData();
            Drone drone = droneJsonDeserializer.deserializeDroneJson(droneJson);
        }

    }

    @Override
    public void onApplicationEvent(MessageReceivedEvent<? extends DroneMessage> event) {
        System.out.println(event.getMessage().getClass().toString() + " Received!");
        DroneMessage droneMessage = event.getMessage();
        droneMessage.accept(droneMessageProcessor);
    }
}
