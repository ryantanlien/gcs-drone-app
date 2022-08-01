package dvd.gcs.app.event;

import dvd.gcs.app.mission.MapPoint;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

public class UpdateDronePositionEvent extends ApplicationEvent {

    private final MapPoint mapPoint;

    public UpdateDronePositionEvent(Object source, Double latitude, Double longitude) {
        super(source);
        this.mapPoint = new MapPoint(latitude, longitude);
    }

    public MapPoint getMapPoint() {
        return this.mapPoint;
    }
}
