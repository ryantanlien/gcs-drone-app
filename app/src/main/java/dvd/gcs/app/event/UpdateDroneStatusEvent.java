package dvd.gcs.app.event;

import org.springframework.context.ApplicationEvent;

public class UpdateDroneStatusEvent extends ApplicationEvent {

    private final String droneCallSign;
    private final String droneModel;

    public UpdateDroneStatusEvent(Object source,
                                  String droneCallSign,
                                  String droneModel) {
        super(source);
        this.droneCallSign = droneCallSign;
        this.droneModel = droneModel;
    }

    public String getDroneCallSign() {
        return this.droneCallSign;
    }

    public String getDroneModel() {
        return this.droneModel;
    }
}
