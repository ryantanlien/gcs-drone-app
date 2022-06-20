package dvd.gcs.app.event;

import dvd.gcs.app.model.Drone;
import org.springframework.context.ApplicationEvent;

public class UpdateDroneModelEvent extends ApplicationEvent {

    private final Drone drone;

    public UpdateDroneModelEvent(Object source, Drone drone) {
        super(source);
        this.drone = drone;
    }

    public Drone getDrone() {
        return drone;
    }
}
