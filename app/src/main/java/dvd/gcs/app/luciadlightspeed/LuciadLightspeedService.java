package dvd.gcs.app.luciadlightspeed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Lazy
public class LuciadLightspeedService {
    private final LuciadMapInterface luciadLightspeedMap;

    @Autowired
    public LuciadLightspeedService(@Qualifier("LuciadLightspeedMap") LuciadMapInterface luciadLightspeedMap) {
        this.luciadLightspeedMap = luciadLightspeedMap;
    }

    // TODO: get drone to call this to update its location on the map
    public void updateLuciadLightspeedDrone(String id, double longitude, double latitude) {
        luciadLightspeedMap.addOrUpdateElement(id, latitude, longitude, 0, false);
    }

    // TODO: get drone to call this to insert its location on the map
    public void createLuciadLightspeedDrone(String id, double longitude, double latitude) {
        luciadLightspeedMap.addOrUpdateElement(id, latitude, longitude, 0, true);
    }

    public void drawNewSearchArea() {
        luciadLightspeedMap.drawNewSearchArea();
    }

    public double getSearchAreaMinX() {
        return luciadLightspeedMap.getSearchAreaMinX();
    }

    public double getSearchAreaMinY() {
        return luciadLightspeedMap.getSearchAreaMinY();
    }

    public double getSearchAreaMaxX() {
        return luciadLightspeedMap.getSearchAreaMaxX();
    }

    public double getSearchAreaMaxY() {
        return luciadLightspeedMap.getSearchAreaMaxY();
    }
}
