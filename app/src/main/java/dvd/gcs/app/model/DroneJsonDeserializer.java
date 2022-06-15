package dvd.gcs.app.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dvd.gcs.app.message.DroneJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DroneJsonDeserializer {

    private final ObjectMapper objectMapper;

    @Autowired
    public DroneJsonDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Drone deserializeDroneJson(DroneJson droneJson) {
        try {
            Drone drone = objectMapper.readValue(droneJson.getJson(), Drone.class);
            return drone;
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
        return null;
    }
}
