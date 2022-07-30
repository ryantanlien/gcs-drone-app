package dvd.gcs.app.message;

import dvd.gcs.app.event.CommandReplyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class DroneMessageQueue {
    private final LinkedList<MessageDispatchEvent<DroneCommandMessage>> messageDispatchEventQueue = new LinkedList<>();

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private boolean isLastMessageResolving = false;

    private void sendNextMessage() {
        MessageDispatchEvent<DroneCommandMessage> dispatchEvent = messageDispatchEventQueue.poll();

        if (dispatchEvent == null) {
            isLastMessageResolving = false;
            return;
        }

        applicationEventPublisher.publishEvent(dispatchEvent);
    }

    private void queue(MessageDispatchEvent<DroneCommandMessage> queueMessageDispatchEvent) {
        this.messageDispatchEventQueue.add(queueMessageDispatchEvent);
        if (!isLastMessageResolving) {
            isLastMessageResolving = true;
            sendNextMessage();
        }
    }

    public void queueLaunchDrone() {
        DroneJson droneJson = new DroneJson("", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage =
                new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.START_TAKEOFF);
        MessageDispatchEvent<DroneCommandMessage> messageDispatchEvent =
                new MessageDispatchEvent<>(this, droneCommandMessage);
        queue(messageDispatchEvent);
    }

    public void queueLandDrone() {
        DroneJson droneJson = new DroneJson("", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage =
                new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.START_LANDING);
        MessageDispatchEvent<DroneCommandMessage> messageDispatchEvent =
                new MessageDispatchEvent<>(this, droneCommandMessage);
        queue(messageDispatchEvent);
    }

    public void queueSaveSearchArea(String missionJson) {
        DroneJson droneJson = new DroneJson(missionJson, DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage =
                new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.UPLOAD_MISSION);
        MessageDispatchEvent<DroneCommandMessage> messageDispatchEvent =
                new MessageDispatchEvent<>(this, droneCommandMessage);
        queue(messageDispatchEvent);
    }

    public void queueStartDroneSearch() {
        DroneJson droneJson = new DroneJson("", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage =
                new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.START_MISSION);
        MessageDispatchEvent<DroneCommandMessage> messageDispatchEvent =
                new MessageDispatchEvent<>(this, droneCommandMessage);
        queue(messageDispatchEvent);
    }

    public void queueStopDroneSearch() {
        DroneJson droneJson = new DroneJson("", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage =
                new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.STOP_MISSION);
        MessageDispatchEvent<DroneCommandMessage> messageDispatchEvent =
                new MessageDispatchEvent<>(this, droneCommandMessage);
        queue(messageDispatchEvent);
    }

    public void queueUpdateDroneGeofence(double geofence) {
        DroneJson droneJson = new DroneJson("" + geofence, DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage =
                new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.SET_GEOFENCE);

        MessageDispatchEvent<DroneCommandMessage> messageDispatchEvent =
                new MessageDispatchEvent<>(this, droneCommandMessage);
        queue(messageDispatchEvent);
    }

    public void queueUpdateDroneSpeed(double speed) {
        DroneJson droneJson = new DroneJson("" + speed, DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage =
                new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.SET_MAX_SPEED);

        MessageDispatchEvent<DroneCommandMessage> messageDispatchEvent =
                new MessageDispatchEvent<>(this, droneCommandMessage);
        queue(messageDispatchEvent);
    }

    public void queueUpdateDroneHeight(double height) {
        DroneJson droneJson = new DroneJson("" + height, DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage =
                new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.SET_ALTITUDE);

        MessageDispatchEvent<DroneCommandMessage> messageDispatchEvent =
                new MessageDispatchEvent<>(this, droneCommandMessage);
        queue(messageDispatchEvent);
    }

    @EventListener
    public void handleCommandReplyEvent(CommandReplyEvent event) {
        isLastMessageResolving = false;
        sendNextMessage();
    }
}
