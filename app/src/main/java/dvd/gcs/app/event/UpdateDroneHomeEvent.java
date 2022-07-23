package dvd.gcs.app.event;

import dvd.gcs.app.mission.MapPoint;
import org.springframework.context.ApplicationEvent;

public class UpdateDroneHomeEvent extends ApplicationEvent {

    private final MapPoint mapPoint;

    public UpdateDroneHomeEvent(Object source, Double homeLatitude, Double homeLongitude) {
        super(source);
        this.mapPoint = new MapPoint(homeLatitude, homeLongitude);
    }

    public MapPoint getMapPoint() {
        return mapPoint;
    }
}
