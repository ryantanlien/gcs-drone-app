package dvd.gcs.app.mission;

public class MapPoint {

    private final Double longitude;
    private final Double latitude;

    public MapPoint(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
