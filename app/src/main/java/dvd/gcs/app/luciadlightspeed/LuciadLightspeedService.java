package dvd.gcs.app.luciadlightspeed;

import dvd.gcs.app.event.UpdateDronePositionEvent;
import dvd.gcs.app.mission.MapPoint;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Lazy
public class LuciadLightspeedService implements ApplicationListener<UpdateDronePositionEvent> {
    private final LuciadMapInterface luciadLightspeedMap;

    @Autowired
    public LuciadLightspeedService(BeanFactory beanFactory) {
        this.luciadLightspeedMap = (LuciadMapInterface) beanFactory.getBean("LuciadLightspeedMap");
    }

    // TODO: get drone to call this to update its location on the map
    public void updateLuciadLightspeedDrone(String id, double latitude, double longitude) {
        luciadLightspeedMap.addOrUpdateElement(id, latitude, longitude, 0, false);
    }

    // TODO: get drone to call this to insert its location on the map
    public void createLuciadLightspeedDrone(String id, double latitude, double longitude) {
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

    @Override
    public void onApplicationEvent(UpdateDronePositionEvent event) {
        MapPoint mapPoint = event.getMapPoint();
        this.updateLuciadLightspeedDrone("Alpha", mapPoint.getLatitude(), mapPoint.getLongitude());
    }
}
