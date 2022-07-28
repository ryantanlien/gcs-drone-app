package dvd.gcs.app.mission;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dvd.gcs.app.event.BuildMissionEvent;
import dvd.gcs.app.message.DroneCommandMessage;
import dvd.gcs.app.message.DroneJson;
import dvd.gcs.app.message.MessageDispatchEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MissionService implements ApplicationListener<BuildMissionEvent> {

    @Autowired
    MissionWaypointBuilder missionWaypointBuilder;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    private static String missionJson;

    private DroneJson waypointsToJson(List<MapPoint> mapPoints) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String mapPointsJson = objectMapper.writeValueAsString(mapPoints.toArray());
            return new DroneJson(mapPointsJson, DroneJson.Type.COMMAND);
        } catch (JsonProcessingException exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return null;
    }

    public static String getMissionJson() throws NoMissionException {
        if (missionJson == null) {
            throw new NoMissionException();
        }
        return missionJson;
    }

    @Override
    public void onApplicationEvent(BuildMissionEvent event) {
        try {
            ArrayList<MapPoint> mapPoints = missionWaypointBuilder.buildMission(event.getLower(),
                    event.getUpper(),
                    event.getSearchPatternType());
            System.out.println(mapPoints.size());

            DroneJson json = waypointsToJson(mapPoints);
            if (json == null) {
                return;
            }
            System.out.println(json.getJson().toString());
            missionJson = json.getJson();
        } catch (Exception e) {
            String message = e.getMessage();
            System.out.println(message);
            e.printStackTrace();
        }
    }
}
