package dvd.gcs.app.luciadlightspeed;

import org.pf4j.ExtensionPoint;

import javafx.embed.swing.SwingNode;

public interface LuciadMapInterface extends ExtensionPoint {

    // TODO: add method to get map info
    public SwingNode getSwingNode();
    // TODO: add method to do map operations

    // TODO:
//    #Create a network loopback adapter with ip address 192.168.x.x/24 as license requires this
//    #FXTouchEventDispatcher is a hack to fix the problem with touch interaction when using javaFX with luciad map.
//
//    Things to work on
//    1) Create a GIS plugin and define the interface for other plugin to use.
//    The plugin should handle all the map operations and displaying of map and other plugin should not import luciad libraries
//    2) Inside the GIS plugin, create a TLspSwingView which will display the map and whatever you need to display
//    in the map and add it inside a swingnode
//    3) The main app should retrieve the swingnode component from the GIS plugin and add it into your JavaFX app
//    4) download free offline maps of singapore from sources like openstreetmap
//    5) load the map into the luciad GIS engine
//    6) Display a drone on the map  (Should be able to recieve the drone telemetry data and display live update
//    of drone position on map when integration is done, GIS plugin should not be the one that recieve telemetry
//    data directly. The plugin that is receiving the telemetry data will call GIS plugin to update the drone position via api)
//
//    e.g. void addOrUpdateElement(String id, double lat, double lon, double height, Image icon);
//
//
//    7) implement user interaction to map to allow creating of waypoints and drawing of polygon for generating of search route.
//    8) if there are multiple drones on map, do collision computation and alert if there will be collision.
}
