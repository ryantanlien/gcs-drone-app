package dvd.gcs.app.mission;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
//TODO: Make exception handling better and more specific
public class MissionWaypointBuilder {

    private final Double minimumWaypointDistanceMetres = 0.5;
    private Double ladderStepSize = minimumWaypointDistanceMetres * 10;
    private final Integer perDegreeDifferenceToMetres = 111000;

    private static final Double VERTICAL_PADDING = 0.05; //Given in percent of total vertical distance

    public enum SearchPatternType {
        HORIZONTAL_LADDER
    }

    //Always assumes that lower is on the lower left and upper is on the upper right
    public ArrayList<MapPoint> buildMission(
            MapPoint lower,
            MapPoint upper,
            SearchPatternType searchPatternType) throws Exception {

        Double absLatitudeDifference = Math.abs(lower.getLatitude() - upper.getLatitude());
        Double absLongitudeDifference = Math.abs(lower.getLongitude() - upper.getLongitude());

        //Pad vertically due to curved movement of drone
        Double absLatDiffWithPadding = absLatitudeDifference * (1 - VERTICAL_PADDING * 2);
        Double baseLatWithPadding = absLatitudeDifference * VERTICAL_PADDING + lower.getLatitude();

        if (!(fromDegreesToMetres(absLatitudeDifference) >= ladderStepSize * 2)) {
            throw new Exception("Horizontal distance between mission bounding points must be greater than minimum waypoint distance of 10m!");
        }
        if (!(fromDegreesToMetres(absLongitudeDifference) >= ladderStepSize * 2)) {
            throw new Exception("Vertical distance between mission bounding points must be greater than minimum way point distance of 10m!");
        }

        if (searchPatternType.equals(SearchPatternType.HORIZONTAL_LADDER)) {
            return createHorizontalLadderMission(
                    fromDegreesToMetres(absLongitudeDifference),
                    fromDegreesToMetres(absLatDiffWithPadding),
                    baseLatWithPadding,
                    lower.getLongitude());
        } else return null;
    }

    private ArrayList<MapPoint> createHorizontalLadderMission(
            Double horizontalDistance,
            Double verticalDistance,
            Double baseLatitude,
            Double baseLongitude) {
        ArrayList<MapPoint> mapPoints = new ArrayList<>();
        ArrayList<Point2D> normalisedPoints = createRelativeHorizontalLadderMission(horizontalDistance, verticalDistance);

        for (int i = 0; i < normalisedPoints.size(); i++) {
            Double latitude = fromMetresToDegrees(normalisedPoints.get(i).getY() * verticalDistance) + baseLatitude;
            Double longitude = fromMetresToDegrees(normalisedPoints.get(i).getX()) * horizontalDistance + baseLongitude;
            mapPoints.add(new MapPoint(latitude, longitude));
        }
        return mapPoints;
    }

    private ArrayList<Point2D> createRelativeHorizontalLadderMission(Double horizontalDistance, Double verticalDistance) {
        ArrayList<Point2D> points = new ArrayList<>();

        double numberOfSteps = horizontalDistance / ladderStepSize + 1;
        System.out.println(horizontalDistance);
        System.out.println(verticalDistance);
        double numberOfWaypointsPerStep = 2;

        double horizontalSpacingNormalised = 1.0 / numberOfSteps;
        double verticalSpacingNormalised = 1.0;

        double horizontalCoordinateNormalised = 0.0;
        for (int i = 0; i < numberOfSteps; i++) {
            int j = 0;
            if (isOdd(i)) {
                double verticalCoordinateNormalised = 0.0;
                do {
                    points.add(new Point2D(horizontalCoordinateNormalised, verticalCoordinateNormalised));
                    j++;
                    verticalCoordinateNormalised += verticalSpacingNormalised;
                } while (j < numberOfWaypointsPerStep);
            } else {
                double verticalCoordinateNormalised = 1.0;
                do {
                    points.add(new Point2D(horizontalCoordinateNormalised, verticalCoordinateNormalised));
                    j++;
                    verticalCoordinateNormalised -= verticalSpacingNormalised;
                } while (j < numberOfWaypointsPerStep);
            }
            horizontalCoordinateNormalised += horizontalSpacingNormalised;
        }
        return points;
    }

    private boolean isOdd(int number) {
        return (number % 2) != 0;
    }

    private Double fromDegreesToMetres(Double degree) {
        return degree * perDegreeDifferenceToMetres;
    }

    private Double fromMetresToDegrees(Double metres) {
        return metres / perDegreeDifferenceToMetres;
    }

    private void setLadderStepSize(Double stepSize) throws IllegalArgumentException {
        if (stepSize < minimumWaypointDistanceMetres) {
            throw new IllegalArgumentException("Ladder Pattern Step Size must be larger than minimum waypoint distance of 0.5m!");
        }
        this.ladderStepSize = stepSize;
    }
}
