package dvd.gcs.app.mission;

public class MapPoint {

    private final Double latitude;
    private final Double longitude;

    public MapPoint(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
