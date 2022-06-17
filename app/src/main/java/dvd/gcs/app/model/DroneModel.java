package dvd.gcs.app.model;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class DroneModel implements ApplicationListener<UpdateDroneModelEvent> {

    public final HashMap<String, Drone> droneHashMap = new HashMap<>();

    //Populate hashmap with dummy data
    public DroneModel() {
        Drone drone = new Drone(
                "Mavic",
                true,
                "Alpha",
                0.2,
                0.2,
                0.2,
                0.2,
                0.2,
                0.0
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
        Double batteryPercent = modelDrone.getBatteryPercent();
        Double altitude = modelDrone.getAltitude();
        Double velocity = modelDrone.getVelocity();
        Double longitude = modelDrone.getLongitude();
        Double latitude = modelDrone.getLatitude();
        Double geoFenceRadius = modelDrone.getGeoFenceRadius();
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
        Drone updatedDrone = new Drone(
                droneModel,
                droneConnection,
                modelDrone.getDroneCallSign(),
                altitude,
                velocity,
                batteryPercent,
                longitude,
                latitude,
                geoFenceRadius
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
        } catch (DroneDoesNotExistException e){
            System.out.println(e.getMessage());
        }
    }
}
