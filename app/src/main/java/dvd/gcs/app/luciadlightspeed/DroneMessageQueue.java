package dvd.gcs.app.luciadlightspeed;

import dvd.gcs.app.luciadlightspeed.event.QueueMessageDispatchEvent;
import dvd.gcs.app.message.DroneCommandMessage;
import dvd.gcs.app.message.DroneJson;
import dvd.gcs.app.message.DroneMessage;
import dvd.gcs.app.message.MessageDispatchEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.LinkedList;

public class DroneMessageQueue {
    private final LinkedList<QueueMessageDispatchEvent> queueMessageDispatchEventQueue = new LinkedList<>();
    private ApplicationEventPublisher applicationEventPublisher; // Springboot event publisher

    public DroneMessageQueue(ApplicationEventPublisher eventPublisher) {
        this.applicationEventPublisher = eventPublisher;
    }

    public LinkedList<QueueMessageDispatchEvent> getPriorityQueue() {
        return queueMessageDispatchEventQueue;
    }

    public void sendNextMessage() {
        QueueMessageDispatchEvent dispatchEvent = queueMessageDispatchEventQueue.poll();

        if (dispatchEvent == null) {
            // no event to dispatch
            return;
        }

        dispatchEvent.publish();
    }

    public void add(QueueMessageDispatchEvent queueMessageDispatchEvent) {
        this.queueMessageDispatchEventQueue.add(queueMessageDispatchEvent);
    }

    public void queueLaunchDrone() {
        DroneJson droneJson = new DroneJson("", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.START_TAKEOFF);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        add(new QueueMessageDispatchEvent(messageDispatchEvent, applicationEventPublisher));
    }

    public void queueLandDrone() {
        DroneJson droneJson = new DroneJson("", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.START_LANDING);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        add(new QueueMessageDispatchEvent(messageDispatchEvent, applicationEventPublisher));
    }

    public void queueSaveSearchArea() {
        DroneJson droneJson = new DroneJson("", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.UPLOAD_MISSION);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        add(new QueueMessageDispatchEvent(messageDispatchEvent, applicationEventPublisher));
    }

    public void queueStartDroneSearch() {
        DroneJson droneJson = new DroneJson("", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.START_MISSION);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        add(new QueueMessageDispatchEvent(messageDispatchEvent, applicationEventPublisher));
    }

    public void queueStopDroneSearch() {
        DroneJson droneJson = new DroneJson("", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.STOP_MISSION);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        add(new QueueMessageDispatchEvent(messageDispatchEvent, applicationEventPublisher));
    }

    public void queueUpdateDroneGeofence(double geofence) {
        DroneJson droneJson = new DroneJson("" + geofence, DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.SET_GEOFENCE);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        add(new QueueMessageDispatchEvent(messageDispatchEvent, applicationEventPublisher));
    }

    public void queueUpdateDroneSpeed(double speed) {
        DroneJson droneJson = new DroneJson("" + speed, DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.SET_MAX_SPEED);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        add(new QueueMessageDispatchEvent(messageDispatchEvent, applicationEventPublisher));
    }

    public void queueUpdateDroneHeight(double height) {
        DroneJson droneJson = new DroneJson("" + height, DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.SET_ALTITUDE);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        add(new QueueMessageDispatchEvent(messageDispatchEvent, applicationEventPublisher));
    }

    public void example() { // example of publishing event
        DroneJson droneJson = new DroneJson(
                "{" +
                        "\"droneCallSign\":\"Alpha\"," +
                        "\"geoFenceRadius\":0.3" + "}", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.SET_GEOFENCE);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        applicationEventPublisher.publishEvent(messageDispatchEvent);
        // given to a class that extends ApplicationListener<DroneCommandMessage>
        // and overrides onApplicationEvent(MessageDispatchEvent<DroneCommandMessage> event)
    }
}
