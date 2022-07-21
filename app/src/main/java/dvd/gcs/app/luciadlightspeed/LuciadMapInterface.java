package dvd.gcs.app.luciadlightspeed;

import org.pf4j.ExtensionPoint;

import javafx.embed.swing.SwingNode;

public interface LuciadMapInterface extends ExtensionPoint {

    // Get SwingNode that contains map information
    public SwingNode getSwingNode();

    // Update a specific element on the map with a new position and Image
    public void addOrUpdateElement(String id, double lat, double lon, double height, boolean isNew);

    // Draw a search area rectangle
    public void drawNewSearchArea();

    public double getSearchAreaMinX();

    public double getSearchAreaMinY();

    public double getSearchAreaMaxX();

    public double getSearchAreaMaxY();
}
