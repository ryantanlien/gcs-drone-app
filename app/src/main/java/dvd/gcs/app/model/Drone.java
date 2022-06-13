package dvd.gcs.app.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Drone {

    private Double batteryPercent;
    private Double altitude;
    private Double velocity;
    private Double geoFenceRadius;
    private String droneModel;
    private String droneCallSign;
    private Boolean droneConnection;
}
