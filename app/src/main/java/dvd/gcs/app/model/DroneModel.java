package dvd.gcs.app.model;

import dvd.gcs.app.event.UpdateDroneHomeEvent;
import dvd.gcs.app.event.UpdateDroneModelEvent;
import dvd.gcs.app.event.UpdateDroneSettingsEvent;
import dvd.gcs.app.event.UpdateDroneStatEvent;
import dvd.gcs.app.event.UpdateDroneStatusEvent;
import dvd.gcs.app.event.UpdateDronePositionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static java.lang.Double.NaN;

@Component
public class DroneModel implements ApplicationListener<UpdateDroneModelEvent> {

    public final HashMap<String, Drone> droneHashMap = new HashMap<>();

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    //Populate hashmap with dummy data
    public DroneModel() {
    }

    public Drone getDrone(String callSign) throws DroneDoesNotExistException {
        Drone drone = droneHashMap.get(callSign);
        if (drone == null) {
            throw new DroneDoesNotExistException(callSign);
        }
        return drone;
    }

    public boolean containsDrone(String callSign) {
        return droneHashMap.containsKey(callSign);
    }

    public Drone addDrone(Drone drone) {
        droneHashMap.put(drone.getDroneCallSign(), drone);
        System.out.println("Drone added! With callsign: " + drone.getDroneCallSign());
        return drone;
    }

    public Drone updateDroneData(Drone drone) throws DroneDoesNotExistException {
        Drone modelDrone = this.getDrone(drone.getDroneCallSign());
        String droneModel = modelDrone.getDroneModel();
        String droneStatus = modelDrone.getDroneStatus();
        Boolean droneConnection = modelDrone.getDroneConnection();
        Integer batteryPercent = modelDrone.getBatteryPercent();
        Double altitude = modelDrone.getAltitude();
        Double velocity = modelDrone.getVelocity();
        Double longitude = modelDrone.getLongitude();
        Double latitude = modelDrone.getLatitude();
        Double geoFenceRadius = modelDrone.getGeoFenceRadius();
        Double maxAltitude = modelDrone.getMaxAltitude();
        Double maxVelocity = modelDrone.getMaxVelocity();
        Double homeLongitude = modelDrone.getHomeLongitude();
        Double homeLatitude = modelDrone.getHomeLatitude();
        if (drone.getDroneModel() != null) {
            droneModel = drone.getDroneModel();
        }
        if (drone.getDroneStatus() != null) {
            droneStatus = drone.getDroneStatus();
        }
        if (drone.getDroneConnection() != null) {
            droneConnection = drone.getDroneConnection();
        }
        if (drone.getBatteryPercent() != null) {
            batteryPercent = drone.getBatteryPercent();
        }
        if (drone.getAltitude() != null) {
            altitude = drone.getAltitude();
        }
        if (drone.getVelocity() != null) {
            velocity = drone.getVelocity();
        }
        if (drone.getGeoFenceRadius() != null) {
            geoFenceRadius = drone.getGeoFenceRadius();
        }
        if (drone.getMaxAltitude() != null) {
            maxAltitude = drone.getMaxAltitude();
        }
        if (drone.getMaxVelocity() != null) {
            maxVelocity = drone.getMaxVelocity();
        }
        if (drone.getHomeLongitude() != null) {
            homeLongitude = drone.getHomeLongitude();
        }
        if (drone.getHomeLatitude() != null) {
            homeLatitude = drone.getHomeLatitude();
        }
        if (drone.getLatitude() != null) {
            latitude = drone.getLatitude();

        }
        if (drone.getLongitude() != null) {
            longitude = drone.getLongitude();
        }
        Drone updatedDrone = new Drone(
                droneModel,
                droneConnection,
                modelDrone.getDroneCallSign(),
                droneStatus,
                altitude,
                velocity,
                batteryPercent,
                longitude,
                latitude,
                geoFenceRadius,
                maxAltitude,
                maxVelocity,
                homeLongitude,
                homeLatitude
        );
        droneHashMap.replace(modelDrone.getDroneCallSign(), updatedDrone);
        return updatedDrone;
    }

    @Override
    public void onApplicationEvent(UpdateDroneModelEvent updateDroneModelEvent) {
        Drone semiUpdatedDrone = updateDroneModelEvent.getDrone();
        try {
            Drone updatedDrone = this.updateDroneData(semiUpdatedDrone);

            //Comment out logging
//            System.out.println("Update Drone Operation Successful!");
//            StringBuilder stringBuilder = new StringBuilder("Updated Drone: ");
//            stringBuilder.append(updatedDrone.toString());
//            System.out.println(stringBuilder.toString());

            applicationEventPublisher.publishEvent(new UpdateDroneStatEvent(this,
                    updatedDrone.getBatteryPercent(),
                    updatedDrone.getAltitude(),
                    updatedDrone.getVelocity(),
                    updatedDrone.getLongitude(),
                    updatedDrone.getLatitude()));

            applicationEventPublisher.publishEvent(new UpdateDroneStatusEvent(this,
                    updatedDrone.getDroneCallSign(),
                    updatedDrone.getDroneModel(),
                    updatedDrone.getDroneStatus()));

            applicationEventPublisher.publishEvent(new UpdateDroneSettingsEvent(this,
                    updatedDrone.getGeoFenceRadius(),
                    updatedDrone.getMaxVelocity(),
                    updatedDrone.getMaxAltitude()));

            /*applicationEventPublisher.publishEvent(new UpdateDronePositionEvent(this,
                    updatedDrone.getLatitude(),
                    updatedDrone.getLongitude()));*/

            applicationEventPublisher.publishEvent(new UpdateDroneHomeEvent(this,
                    updatedDrone.getHomeLatitude(),
                    updatedDrone.getHomeLongitude()));

        } catch (DroneDoesNotExistException e){
            System.out.println(e.getMessage());
        }
    }
}
