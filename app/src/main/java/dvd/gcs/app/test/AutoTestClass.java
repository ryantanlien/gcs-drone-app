package dvd.gcs.app.test;

import dvd.gcs.app.event.BuildMissionEvent;
import dvd.gcs.app.message.DroneCommandMessage;
import dvd.gcs.app.message.DroneJson;
import dvd.gcs.app.message.DroneMessage;
import dvd.gcs.app.message.MessageDispatchEvent;
import dvd.gcs.app.mission.MapPoint;
import dvd.gcs.app.mission.MissionWaypointBuilder;
import dvd.gcs.app.model.Drone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class AutoTestClass {

    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public AutoTestClass(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
        publishBuildMissionEvent();
    }

    public void publishBuildMissionEvent() {
        MapPoint mapBottomPoint = new MapPoint(1.3294993357511558, 103.78537511871468);
        MapPoint mapTopPoint = new MapPoint(1.3291409109811538, 103.7850430467549);
        applicationEventPublisher.publishEvent(
                new BuildMissionEvent(this, mapBottomPoint, mapTopPoint, MissionWaypointBuilder.SearchPatternType.HORIZONTAL_LADDER));
    }

    public void publishSetGeoFenceEvent() {
        DroneJson droneJson = new DroneJson(
                "0.3", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.SET_GEOFENCE);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        applicationEventPublisher.publishEvent(messageDispatchEvent);
    }

    public void publishSetMaxSpeedEvent() {
        DroneJson droneJson = new DroneJson(
                "10.0", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.SET_MAX_SPEED);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        applicationEventPublisher.publishEvent(messageDispatchEvent);
    }

    public void publishSetAltitudeEvent() {
        DroneJson droneJson = new DroneJson(
                "30.0", DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.SET_ALTITUDE);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        applicationEventPublisher.publishEvent(messageDispatchEvent);
    }

    public void publishStartLandingEvent() {
        DroneJson droneJson = new DroneJson(null, DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.START_LANDING);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        applicationEventPublisher.publishEvent(messageDispatchEvent);
    }


    public void publishStartTakeOffEvent() {
        DroneJson droneJson = new DroneJson(null, DroneJson.Type.COMMAND);

        DroneCommandMessage droneCommandMessage
                = new DroneCommandMessage(droneJson, DroneCommandMessage.CommandType.START_TAKEOFF);

        MessageDispatchEvent<DroneMessage> messageDispatchEvent = new MessageDispatchEvent<>(this, droneCommandMessage);
        applicationEventPublisher.publishEvent(messageDispatchEvent);
    }
}
