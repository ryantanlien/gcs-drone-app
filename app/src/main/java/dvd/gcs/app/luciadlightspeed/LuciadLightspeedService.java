package dvd.gcs.app.luciadlightspeed;

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
    LuciadMapInterface luciadLightspeedMap; // TODO: may not work

    @Autowired
    ApplicationEventPublisher applicationEventPublisher; // Springboot event publisher

    public void updateLuciadLightspeedDrone(String id, double longitude, double latitude) {
        luciadLightspeedMap.addOrUpdateElement(id, latitude, longitude, 0, false);
    }

    public void createLuciadLightspeedDrone(String id, double longitude, double latitude) {
        luciadLightspeedMap.addOrUpdateElement(id, latitude, longitude, 0, true);
    }

    public void startDroneSearch() {
        // TODO: implement
    }

    public void stopDroneSearch() {
        // TODO: implement
    }

    public void updateDrone(double speed, double height, double geofence) {
        // update geofence
        DroneJson droneJsonGeofence = new DroneJson(
                "{" +
                        "\"droneCallSign\":\"Alpha\"," +
                        "\"geoFenceRadius\":" + geofence + "}", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessageGeofence
                = new DroneCommandMessage(droneJsonGeofence, DroneCommandMessage.CommandType.SET_GEOFENCE);

        MessageDispatchEvent<DroneMessage> messageDispatchEventGeofence = new MessageDispatchEvent<>(this, droneCommandMessageGeofence);
        applicationEventPublisher.publishEvent(messageDispatchEventGeofence);

        // update speed
        DroneJson droneJsonSpeed = new DroneJson(
                "{" +
                        "\"droneCallSign\":\"Alpha\"," +
                        "\"droneSpeed\":" + speed + "}", DroneJson.Type.COMMAND); // TODO: fix formatting

        DroneCommandMessage droneCommandMessageDroneSpeed
                = new DroneCommandMessage(droneJsonSpeed, DroneCommandMessage.CommandType.SET_GEOFENCE); // TODO: there is no set speed currently

        MessageDispatchEvent<DroneMessage> messageDispatchEventDroneSpeed = new MessageDispatchEvent<>(this, droneCommandMessageDroneSpeed);
        applicationEventPublisher.publishEvent(messageDispatchEventDroneSpeed);

        // update height
        DroneJson droneJsonHeight = new DroneJson(
                "{" +
                        "\"droneCallSign\":\"Alpha\"," +
                        "\"droneHeight\":" + height + "}", DroneJson.Type.COMMAND); // TODO: fix formatting

        DroneCommandMessage droneCommandMessageDroneHeight
                = new DroneCommandMessage(droneJsonHeight, DroneCommandMessage.CommandType.SET_ALTITUDE);

        MessageDispatchEvent<DroneMessage> messageDispatchEventDroneHeight = new MessageDispatchEvent<>(this, droneCommandMessageDroneHeight);
        applicationEventPublisher.publishEvent(messageDispatchEventDroneHeight);
    }

    public void example() { // example
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
