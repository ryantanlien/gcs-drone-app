package dvd.gcs.app.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dvd.gcs.app.message.DroneJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class DroneJsonDeserializer {

    private final ObjectMapper objectMapper;

    @Autowired
    public DroneJsonDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.enable(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS.mappedFeature());
    }

    public Drone deserializeDroneJson(DroneJson droneJson) {
        try {
            Drone drone = objectMapper.readValue(droneJson.getJson(), Drone.class);
            requireNonNull(drone);
            checkDroneDeserialization(droneJson, drone);
            return drone;
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
        return null;
    }

    public void checkDroneDeserialization(DroneJson droneJson, Drone drone) throws NullPointerException {
        requireNonNull(drone.getDroneCallSign());
        if (droneJson.getType() == DroneJson.Type.TELEMETRY) {
            requireNonNull(drone.getLongitude());
            requireNonNull(drone.getLatitude());
            requireNonNull(drone.getVelocity());
            requireNonNull(drone.getAltitude());
            requireNonNull(drone.getBatteryPercent());
        }
    }
}
