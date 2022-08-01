package dvd.gcs.app.event;

import org.springframework.context.ApplicationEvent;

public class UpdateDroneStatusEvent extends ApplicationEvent {

    private final String droneCallSign;
    private final String droneModel;
    private final String droneStatus;

    public UpdateDroneStatusEvent(Object source,
                                  String droneCallSign,
                                  String droneModel,
                                  String droneStatus) {
        super(source);
        this.droneCallSign = droneCallSign;
        this.droneModel = droneModel;
        this.droneStatus = droneStatus;
    }

    public String getDroneCallSign() {
        return this.droneCallSign;
    }

    public String getDroneModel() {
        return this.droneModel;
    }
    public String getDroneStatus() {
        return this.droneStatus;
    }
}
