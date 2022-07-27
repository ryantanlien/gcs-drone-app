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

    /**
     * Creates a drone icon on the map at the specified longitude and latitude
     * Uses a String id to identify each unique drone
     *
     * @param id            the String to uniquely identify the drone
     * @param longitude     longitude of drone
     * @param latitude      latitide of drone
     */
    // TODO: get drone to call this to insert its location on the map
    public void createLuciadLightspeedDrone(String id, double longitude, double latitude) {
        luciadLightspeedMap.addOrUpdateElement(id, latitude, longitude, 0, true);
    }

    /**
     * Updates a drone icon on the map at the specified longitude and latitude
     * Uses a String id to identify each unique drone
     * To be called periodically to continuously update drone location
     *
     * @param id            the String to uniquely identify the drone
     * @param longitude     longitude of drone
     * @param latitude      latitide of drone
     */
    // TODO: get drone to call this to update its location on the map
    public void updateLuciadLightspeedDrone(String id, double latitude, double longitude) {
        luciadLightspeedMap.addOrUpdateElement(id, latitude, longitude, 0, false);
    }

    /**
     * Creates a drone home icon on the map at the specified longitude and latitude
     * Uses a String id to identify each unique drone home
     *
     * @param id            the String to uniquely identify the drone home
     * @param longitude     longitude of drone home
     * @param latitude      latitide of drone home
     */
    // TODO: get drone to call this to insert its home location on the map
    public void createLuciadLightspeedDroneHome(String id, double longitude, double latitude) {
        luciadLightspeedMap.addOrUpdateHomeElement(id, latitude, longitude, 0, true);
    }

    /**
     * Updates a drone home icon on the map at the specified longitude and latitude
     * Uses a String id to identify each unique drone home
     *
     * @param id            the String to uniquely identify the drone home
     * @param longitude     longitude of drone home
     * @param latitude      latitide of drone home
     */
    // TODO: get drone to call this to update its home location on the map
    public void updateLuciadLightspeedDroneHome(String id, double longitude, double latitude) {
        luciadLightspeedMap.addOrUpdateHomeElement(id, latitude, longitude, 0, false);
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
