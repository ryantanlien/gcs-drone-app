package dvd.gcs.app.model;

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
