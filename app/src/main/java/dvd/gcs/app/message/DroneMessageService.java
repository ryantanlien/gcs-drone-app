package dvd.gcs.app.message;

import dvd.gcs.app.model.Drone;
import dvd.gcs.app.model.DroneJsonDeserializer;
import dvd.gcs.app.event.UpdateDroneModelEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DroneMessageService implements ApplicationListener<MessageDispatchEvent<? extends DroneMessage>> {

    @Autowired
    DroneJsonDeserializer droneJsonDeserializer;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    List<MessageTransmitEventListener<DroneMessage>> listeners = new ArrayList<>();

    DroneMessageProcessor droneMessageProcessor = new DroneMessageProcessor();

    private interface MessageProcessor {
        void process(DroneMessage droneMessage);
        void process(DroneTelemetryMessage droneTelemetryMessage);
        void process(DroneCommandReplyMessage droneCommandReplyMessage);
        void process(DroneCommandMessage droneCommandMessage);
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

        @Override
        public void process(DroneCommandReplyMessage droneCommandReplyMessage) {
            if (droneCommandReplyMessage.getCommandStatus().equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {
                DroneJson droneJson = droneCommandReplyMessage.getData();
                Drone drone = droneJsonDeserializer.deserializeDroneJson(droneJson);
                applicationEventPublisher.publishEvent(new UpdateDroneModelEvent(this, drone));
            } else {
                //TODO: Add logic to update ui based on command failure later on.
            }
        }

        //A ui class will create the droneCommandMessage and pass it to the service
        @Override
        public void process(DroneCommandMessage droneCommandMessage) {
            MessageTransmitEvent<DroneMessage> messageTransmitEvent
                    = new MessageTransmitEvent<>(this, droneCommandMessage);
            listeners.forEach(listener -> listener.receiveEvent(messageTransmitEvent));
        }
    }

    @Override
    public void onApplicationEvent(MessageDispatchEvent<? extends DroneMessage> event) {
        System.out.println(event.getMessage().getClass().toString() + " Received!");
        DroneMessage droneMessage = event.getMessage();
        droneMessage.accept(droneMessageProcessor);
    }

    public void addListener(MessageTransmitEventListener<DroneMessage> listener) {
        listeners.add(listener);
    }
}
