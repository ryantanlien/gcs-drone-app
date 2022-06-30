package dvd.gcs.app.model;

public class DroneDoesNotExistException extends Exception {

    public DroneDoesNotExistException(String callSign) {
        super("Drone with callsign: " + callSign + " does not exist in system!");
    }
}
