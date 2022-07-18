package dvd.gcs.app.luciadlightspeed;

import dvd.gcs.app.luciadlightspeed.event.DroneMessageDispatchEvent;
import dvd.gcs.app.message.DroneCommandMessage;
import dvd.gcs.app.message.DroneJson;
import dvd.gcs.app.message.DroneMessage;
import dvd.gcs.app.message.MessageDispatchEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Lazy
public class LuciadLightspeedService {
    @Autowired
    @Qualifier("LuciadLightspeedMap")
    private LuciadMapInterface luciadLightspeedMap;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher; // Springboot event publisher

    private final DroneMessageQueue droneMessageQueue = new DroneMessageQueue();

    public void updateLuciadLightspeedDrone(String id, double longitude, double latitude) {
        luciadLightspeedMap.addOrUpdateElement(id, latitude, longitude, 0, false);
    }

    public void createLuciadLightspeedDrone(String id, double longitude, double latitude) {
        luciadLightspeedMap.addOrUpdateElement(id, latitude, longitude, 0, true);
    }

    public void sendNextMessage() {
        droneMessageQueue.sendNextMessage();
    }

    public void queueStartDroneSearch() {
        DroneJson droneJson = new DroneJson("", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.START_MISSION);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        droneMessageQueue.add(new DroneMessageDispatchEvent(messageDispatchEvent, applicationEventPublisher));
    }

    public void queueStopDroneSearch() {
        DroneJson droneJson = new DroneJson("", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.STOP_MISSION);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        droneMessageQueue.add(new DroneMessageDispatchEvent(messageDispatchEvent, applicationEventPublisher));
    }

    public void queueUpdateDroneGeofence(double geofence) {
        DroneJson droneJson = new DroneJson("" + geofence, DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.SET_GEOFENCE);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        droneMessageQueue.add(new DroneMessageDispatchEvent(messageDispatchEvent, applicationEventPublisher));
    }

    public void queueUpdateDroneSpeed(double speed) {
        DroneJson droneJson = new DroneJson("" + speed, DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.SET_MAX_SPEED);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        droneMessageQueue.add(new DroneMessageDispatchEvent(messageDispatchEvent, applicationEventPublisher));
    }

    public void queueUpdateDroneHeight(double height) {
        DroneJson droneJson = new DroneJson("" + height, DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.SET_ALTITUDE);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        droneMessageQueue.add(new DroneMessageDispatchEvent(messageDispatchEvent, applicationEventPublisher));
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
