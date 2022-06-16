package dvd.gcs.app.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class DroneModel {

    public final HashMap<String, Drone> droneHashMap = new HashMap<>();

    public DroneModel() {
    }

    public Drone getDrone(String callSign) {
        Drone drone = droneHashMap.get(callSign);
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

    public Drone updateDroneData(Drone drone) {
        Drone modelDrone = droneHashMap.get(drone.getDroneCallSign());
        String droneModel = modelDrone.getDroneModel();
        Boolean droneConnection = modelDrone.getDroneConnection();
        Double batteryPercent = modelDrone.getBatteryPercent();
        Double altitude = modelDrone.getAltitude();
        Double velocity = modelDrone.getVelocity();
        if (drone.getDroneModel() != null) {
            droneModel = drone.getDroneModel();
        }
        if (drone.getDroneConnection() != null ) {
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
        Drone updatedDrone = new Drone(
                droneModel,
                droneConnection,
                modelDrone.getDroneCallSign(),
                altitude,
                velocity,
                batteryPercent
        );
        droneHashMap.replace(modelDrone.getDroneCallSign(), updatedDrone);
        return updatedDrone;
    }
}
