package dvd.gcs.app.mission;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class MapPoint {

    private final Double latitude;
    private final Double longitude;

    public MapPoint(@JsonProperty("latitude") Double latitude,
                    @JsonProperty("longitude") Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "MapPoint -> { Latitude: " + latitude + " | " + "Longitude: " + longitude + " }";
    }
}
