package dvd.gcs.app.model;

import dvd.gcs.app.event.UpdateDroneModelEvent;
import dvd.gcs.app.event.UpdateDroneSettingsEvent;
import dvd.gcs.app.event.UpdateDroneStatEvent;
import dvd.gcs.app.event.UpdateDroneStatusEvent;
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
        //Demo sake, TODO: Remove later when Drone connection is implemented
        Drone drone = new Drone(
                "Mavic",
                true,
                "Alpha",
                0.2,
                0.2,
                1,
                NaN,
                NaN,
                0.0,
                50.0,
                15.0
        );
        addDrone(drone);
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
        Boolean droneConnection = modelDrone.getDroneConnection();
        Integer batteryPercent = modelDrone.getBatteryPercent();
        Double altitude = modelDrone.getAltitude();
        Double velocity = modelDrone.getVelocity();
        Double longitude = modelDrone.getLongitude();
        Double latitude = modelDrone.getLatitude();
        Double geoFenceRadius = modelDrone.getGeoFenceRadius();
        Double maxAltitude = modelDrone.getMaxAltitude();
        Double maxVelocity = modelDrone.getMaxVelocity();
        if (drone.getDroneModel() != null) {
            droneModel = drone.getDroneModel();
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
        Drone updatedDrone = new Drone(
                droneModel,
                droneConnection,
                modelDrone.getDroneCallSign(),
                altitude,
                velocity,
                batteryPercent,
                longitude,
                latitude,
                geoFenceRadius,
                maxAltitude,
                maxVelocity
        );
        droneHashMap.replace(modelDrone.getDroneCallSign(), updatedDrone);
        return updatedDrone;
    }

    @Override
    public void onApplicationEvent(UpdateDroneModelEvent updateDroneModelEvent) {
        Drone semiUpdatedDrone = updateDroneModelEvent.getDrone();
        try {
            Drone updatedDrone = this.updateDroneData(semiUpdatedDrone);
            System.out.println("Update Drone Operation Successful!");
            StringBuilder stringBuilder = new StringBuilder("Updated Drone: ");
            stringBuilder.append(updatedDrone.toString());
            System.out.println(stringBuilder.toString());

            applicationEventPublisher.publishEvent(new UpdateDroneStatEvent(this,
                    updatedDrone.getBatteryPercent(),
                    updatedDrone.getAltitude(),
                    updatedDrone.getVelocity(),
                    updatedDrone.getLongitude(),
                    updatedDrone.getLatitude()));

            applicationEventPublisher.publishEvent(new UpdateDroneStatusEvent(this,
                    updatedDrone.getDroneCallSign(),
                    updatedDrone.getDroneModel()));

            applicationEventPublisher.publishEvent(new UpdateDroneSettingsEvent(this,
                    updatedDrone.getGeoFenceRadius(),
                    updatedDrone.getMaxVelocity(),
                    updatedDrone.getMaxAltitude()));

        } catch (DroneDoesNotExistException e){
            System.out.println(e.getMessage());
        }
    }
}
