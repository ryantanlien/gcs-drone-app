package dvd.gcs.app.message;

import dvd.gcs.app.event.UploadDroneMissionEvent;
import dvd.gcs.app.model.Drone;
import dvd.gcs.app.model.DroneJsonDeserializer;

import dvd.gcs.app.event.UpdateDroneModelEvent;
import dvd.gcs.app.event.SetAltitudeReplyEvent;
import dvd.gcs.app.event.SetGeofenceReplyEvent;
import dvd.gcs.app.event.SetMaxSpeedReplyEvent;
import dvd.gcs.app.event.StartDroneSearchReplyEvent;
import dvd.gcs.app.event.StartLandingReplyEvent;
import dvd.gcs.app.event.StartTakeoffReplyEvent;
import dvd.gcs.app.event.StopDroneSearchReplyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DroneMessageService implements ApplicationListener<MessageDispatchEvent<? extends DroneMessage>> {

    @Autowired
    DroneMessageQueue droneMessageQueue;
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

            //Logging
            System.out.println(droneCommandReplyMessage.getClass() + " received by " + this.getClass() + "!");
            System.out.println("DroneCommandReplyMessage Command Type: " + droneCommandReplyMessage.getCommandType());
            System.out.println("DroneCommandReplyMessage Command Status: " + droneCommandReplyMessage.getCommandStatus().toString());

            if (droneCommandReplyMessage.getData() != null) {

            }

            if (droneCommandReplyMessage.getCommandStatus().equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {
                DroneCommandMessage.CommandType droneCommandType = droneCommandReplyMessage.getCommandType();

                switch (droneCommandType) {
                    case SET_GEOFENCE -> {
                        DroneJson droneJson = droneCommandReplyMessage.getData();
                        Drone drone = droneJsonDeserializer.deserializeDroneJson(droneJson);
                        applicationEventPublisher.publishEvent(new UpdateDroneModelEvent(this, drone));
                        applicationEventPublisher.publishEvent(
                                new SetGeofenceReplyEvent(this,
                                    DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS));
                    }
                    case SET_ALTITUDE -> {
                        DroneJson droneJson = droneCommandReplyMessage.getData();
                        Drone drone = droneJsonDeserializer.deserializeDroneJson(droneJson);
                        applicationEventPublisher.publishEvent(new UpdateDroneModelEvent(this, drone));
                        applicationEventPublisher.publishEvent(
                                new SetAltitudeReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS));
                    }
                    case SET_MAX_SPEED -> {
                        DroneJson droneJson = droneCommandReplyMessage.getData();
                        Drone drone = droneJsonDeserializer.deserializeDroneJson(droneJson);
                        applicationEventPublisher.publishEvent(new UpdateDroneModelEvent(this, drone));
                        applicationEventPublisher.publishEvent(
                                new SetMaxSpeedReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE));
                    }
                    case START_TAKEOFF -> {
                        applicationEventPublisher.publishEvent(
                                new StartTakeoffReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS));
                    }
                    case START_LANDING -> {
                        applicationEventPublisher.publishEvent(
                                new StartLandingReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS));
                    }
                    case START_MISSION -> {
                        applicationEventPublisher.publishEvent(
                                new StartDroneSearchReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS));
                    }

                    case STOP_MISSION -> {
                        applicationEventPublisher.publishEvent(
                                new StopDroneSearchReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS));
                    }
                    case UPLOAD_MISSION -> {
                        applicationEventPublisher.publishEvent(
                                new UploadDroneMissionEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS));
                    }
                }
            } else if (droneCommandReplyMessage.getCommandStatus().equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {
                DroneCommandMessage.CommandType droneCommandType = droneCommandReplyMessage.getCommandType();

                switch (droneCommandType) {
                    case SET_GEOFENCE -> {
                        applicationEventPublisher.publishEvent(
                                new SetGeofenceReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE));
                    }
                    case SET_ALTITUDE -> {
                        applicationEventPublisher.publishEvent(
                                new SetAltitudeReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE));
                    }
                    case SET_MAX_SPEED -> {
                        applicationEventPublisher.publishEvent(
                                new SetMaxSpeedReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE));
                    }
                    case START_TAKEOFF -> {
                        applicationEventPublisher.publishEvent(
                                new StartTakeoffReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE));
                    }
                    case START_LANDING -> {
                        applicationEventPublisher.publishEvent(
                                new StartLandingReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE));
                    }
                    case START_MISSION -> {
                        applicationEventPublisher.publishEvent(
                                new StartDroneSearchReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE));
                    }
                    case STOP_MISSION -> {
                        applicationEventPublisher.publishEvent(
                                new StopDroneSearchReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE));
                    }
                    case UPLOAD_MISSION -> {
                        applicationEventPublisher.publishEvent(
                                new UploadDroneMissionEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE));
                    }
                }
            } else {
                DroneCommandMessage.CommandType droneCommandType = droneCommandReplyMessage.getCommandType();
                switch (droneCommandType) {
                    case SET_GEOFENCE -> {
                        applicationEventPublisher.publishEvent(
                                new SetGeofenceReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.FAILED_TO_SEND));
                    }
                    case SET_ALTITUDE -> {
                        applicationEventPublisher.publishEvent(
                                new SetAltitudeReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.FAILED_TO_SEND));
                    }
                    case SET_MAX_SPEED -> {
                        applicationEventPublisher.publishEvent(
                                new SetMaxSpeedReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.FAILED_TO_SEND));
                    }
                    case START_TAKEOFF -> {
                        applicationEventPublisher.publishEvent(
                                new StartTakeoffReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.FAILED_TO_SEND));
                    }
                    case START_LANDING -> {
                        applicationEventPublisher.publishEvent(
                                new StartLandingReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.FAILED_TO_SEND));
                    }
                    case START_MISSION -> {
                        applicationEventPublisher.publishEvent(
                                new StartDroneSearchReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.FAILED_TO_SEND));
                    }
                    case STOP_MISSION -> {
                        applicationEventPublisher.publishEvent(
                                new StopDroneSearchReplyEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.FAILED_TO_SEND));
                    }
                    case UPLOAD_MISSION -> {
                        applicationEventPublisher.publishEvent(
                                new UploadDroneMissionEvent(this,
                                        DroneCommandReplyMessage.CommandStatus.FAILED_TO_SEND));
                    }
                }
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
//        System.out.println(event.getMessage().getClass().toString() + " Received!");
        DroneMessage droneMessage = event.getMessage();
        droneMessage.accept(droneMessageProcessor);
    }

    public void addListener(MessageTransmitEventListener<DroneMessage> listener) {
        listeners.add(listener);
    }

    public DroneMessageQueue getDroneMessageQueue() {
        return this.droneMessageQueue;
    }
}
