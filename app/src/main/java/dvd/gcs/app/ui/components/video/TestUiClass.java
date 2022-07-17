package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.event.BuildMissionEvent;
import dvd.gcs.app.message.DroneCommandMessage;
import dvd.gcs.app.message.DroneJson;
import dvd.gcs.app.message.DroneMessage;
import dvd.gcs.app.message.MessageDispatchEvent;
import dvd.gcs.app.mission.MapPoint;
import dvd.gcs.app.mission.MissionWaypointBuilder;
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
        publishBuildMissionEvent();
        publishEvent();
    }

    public void publishBuildMissionEvent() {
        MapPoint mapBottomPoint = new MapPoint(88.0, 89.0);
        MapPoint mapTopPoint = new MapPoint(88.0001, 89.0001);
        applicationEventPublisher.publishEvent(new BuildMissionEvent(this, mapBottomPoint, mapTopPoint, MissionWaypointBuilder.SearchPatternType.HORIZONTAL_LADDER));
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
