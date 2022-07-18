package dvd.gcs.app.event;

import dvd.gcs.app.mission.MapPoint;
import dvd.gcs.app.mission.MissionWaypointBuilder;
import org.springframework.context.ApplicationEvent;

public class BuildMissionEvent extends ApplicationEvent {

    private final MapPoint lower;
    private final MapPoint upper;
    private final MissionWaypointBuilder.SearchPatternType searchPatternType;

    public BuildMissionEvent(Object source,
                             MapPoint lower,
                             MapPoint upper,
                             MissionWaypointBuilder.SearchPatternType searchPatternType) {
        super(source);
        this.lower = lower;
        this.upper = upper;
        this.searchPatternType = searchPatternType;
    }

    public dvd.gcs.app.mission.MapPoint getLower() {
        return lower;
    }

    public dvd.gcs.app.mission.MapPoint getUpper() {
        return upper;
    }

    public MissionWaypointBuilder.SearchPatternType getSearchPatternType() {
        return searchPatternType;
    }
}
