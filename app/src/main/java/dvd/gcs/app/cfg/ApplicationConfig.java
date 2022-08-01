package dvd.gcs.app.cfg;

import dvd.gcs.app.luciadlightspeed.LuciadLightspeedService;
import dvd.gcs.app.model.Drone;
import dvd.gcs.app.model.DroneModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class ApplicationConfig {

    private DroneModel droneModel;
    private LuciadLightspeedService luciadLightspeedService;

    @Autowired
    public ApplicationConfig(DroneModel droneModel, LuciadLightspeedService luciadLightspeedService) {
        this.droneModel = droneModel;
        this.luciadLightspeedService = luciadLightspeedService;
    }

    public void initializeAlphaDroneDefault() {
        Drone drone = new Drone(
                "Mavic",
                true,
                "Alpha",
                "Idle",
                0.2,
                0.2,
                1,
                103.78537511871468,
                1.3294993357511558,
                0.0,
                50.0,
                15.0,
                103.78537511871468,
                1.3294993357511558
        );
        droneModel.addDrone(drone);
        this.luciadLightspeedService.createLuciadLightspeedDrone("Alpha", 1.3294993357511558, 103.78537511871468);
        this.luciadLightspeedService.createLuciadLightspeedDroneHome("AlphaHome", 1.3294993357511558, 103.78537511871468);
    }
}
