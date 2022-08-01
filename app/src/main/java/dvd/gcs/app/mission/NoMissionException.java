package dvd.gcs.app.mission;

public class NoMissionException extends Exception {

    private static String message = "No Mission Built! Build mission before sending!";

    public NoMissionException() {
        super(message);
    }
}
