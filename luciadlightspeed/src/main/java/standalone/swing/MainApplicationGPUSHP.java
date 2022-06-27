package standalone.swing;

import com.luciad.format.shp.TLcdSHPModelDecoder;
import com.luciad.geodesy.TLcdGeodeticDatum;
import com.luciad.model.ILcdModel;
import com.luciad.model.ILcdModelDecoder;
import com.luciad.model.TLcdCompositeModelDecoder;
import com.luciad.projection.TLcdEquidistantCylindrical;
import com.luciad.reference.TLcdGridReference;
import com.luciad.shape.shape2D.TLcdLonLatPoint;
import com.luciad.shape.shape2D.TLcdXYPoint;
import com.luciad.util.service.TLcdServiceLoader;
import com.luciad.view.lightspeed.ILspAWTView;
import com.luciad.view.lightspeed.ILspView;
import com.luciad.view.lightspeed.TLspSwingView;
import com.luciad.view.lightspeed.layer.ILspLayer;
import com.luciad.view.lightspeed.layer.shape.TLspShapeLayerBuilder;
import com.luciad.view.lightspeed.painter.grid.TLspLonLatGridLayerBuilder;
import com.luciad.view.lightspeed.style.TLspIconStyle;
import com.luciad.view.lightspeed.util.TLspViewNavigationUtil;
import com.luciad.view.lightspeed.util.TLspViewTransformationUtil;
import com.luciad.view.swing.TLcdLayerTree;
import javafx.scene.image.Image;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Main class used to test running luciad lightspeed standalone, which is then used to update LuciadMap.java
 */
public class MainApplicationGPUSHP {
    public static String[] shpStrings = { // order of strings matters
            "singapore-msia-brunei/gis_osm_landuse_a_free_1.shp",
            "singapore-msia-brunei/gis_osm_pois_a_free_1.shp",
            "singapore-msia-brunei/gis_osm_pofw_a_free_1.shp",
            "singapore-msia-brunei/gis_osm_natural_a_free_1.shp",
            "singapore/SGP_adm0.shp",
            "singapore-msia-brunei/gis_osm_buildings_a_free_1.shp",
            "singapore-msia-brunei/gis_osm_railways_free_1.shp",
            "singapore-msia-brunei/gis_osm_roads_free_1.shp",
    };
    public static String[] unusedShpStrings = {
            "singapore-msia-brunei/gis_osm_natural_free_1.shp",
            "singapore-msia-brunei/gis_osm_places_a_free_1.shp",
            "singapore-msia-brunei/gis_osm_places_free_1.shp",
            "singapore-msia-brunei/gis_osm_pofw_free_1.shp",
            "singapore-msia-brunei/gis_osm_pois_free_1.shp",
            "singapore-msia-brunei/gis_osm_traffic_a_free_1.shp",
            "singapore-msia-brunei/gis_osm_traffic_free_1.shp",
            "singapore-msia-brunei/gis_osm_transport_a_free_1.shp",
            "singapore-msia-brunei/gis_osm_transport_free_1.shp",
            "singapore-msia-brunei/gis_osm_water_a_free_1.shp",
            "singapore-msia-brunei/gis_osm_waterways_free_1.shp",
    };

    public JFrame createUI() {
        JFrame frame = new JFrame("Lightspeed demo application");

        ILspAWTView view = createView();
        frame.add(view.getHostComponent(), BorderLayout.CENTER);

        TLspViewTransformationUtil.setup2DView(
                view,
                new TLcdGridReference(new TLcdGeodeticDatum(),
                        new TLcdEquidistantCylindrical()),
                true
        );

        addData(view);

        JComponent layerControl = createLayerControl(view);
        frame.add(layerControl, BorderLayout.EAST);

        view.addLayer(createGridLayer());

        //////////////////////////////////////////////
        // Auto navigate to Singapore
        TLspViewNavigationUtil navigationUtil = new TLspViewNavigationUtil(view);
        // 504 237 factor 1500
        TLcdXYPoint singaporeXYPoint = new TLcdXYPoint(504.9999, 237.9999);
        TLcdLonLatPoint singaporeLonLatPoint = new TLcdLonLatPoint(100, 70);
        navigationUtil.zoom(singaporeXYPoint, 1500);
        //////////////////////////////////////////////

        //////////////////////////////////////////////
        // Add drone icon to map
        // TODO: Add drone icon to map
        // TODO: Layer.getmodel.addelement??
        DroneIconModel drone1 = new DroneIconModel();
        //////////////////////////////////////////////

        //////////////////////////////////////////////
        // JToolBar for 2D and 3D views
        JToolBar toolBar = new JToolBar();

        JRadioButton b2d = new JRadioButton(createSwitchTo2DAction(view));
        b2d.setSelected(true); //start with a 2D view
        JRadioButton b3d = new JRadioButton(createSwitchTo3DAction(view));

        //Place the buttons in a ButtonGroup.
        //This ensures that only one of them can be selected at the same time
        ButtonGroup group = new ButtonGroup();
        group.add(b2d);
        group.add(b3d);

        toolBar.add(b2d);
        toolBar.add(b3d);

        frame.add(toolBar, BorderLayout.NORTH);
        //////////////////////////////////////////////

        frame.setSize(2000, 1500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    public void addOrUpdateElement(String id, double lat, double lon, double height, Image icon) {

    }

    public ILspAWTView createView() {
        // return TLspViewBuilder.newBuilder().buildAWTView(); // Old usage, results in conflicts
        return new TLspSwingView();
    }

//    private TLspFXView createFxView() {
//        return TLspViewBuilder.newBuilder().buildFXView();
//    }

    static void addData(ILspView view) {
        try {
            for (String shpString : shpStrings) {
                ILcdModel shpModel = createSHPModel(shpString);
                view.addLayer(createLayer(shpModel));
            }

//            ILcdModel rasterModel = createRasterModel();
//            view.addLayer(createLayer(rasterModel));
        } catch (IOException e) {
            throw new RuntimeException("Problem during data decoding", e);
        }
    }

    private static ILcdModel createSHPModel(String shpString) throws IOException {
        // This composite decoder can decode all supported formats
        TLcdSHPModelDecoder decoder = new TLcdSHPModelDecoder();
                // new TLcdCompositeModelDecoder(TLcdServiceLoader.getInstance(ILcdModelDecoder.class));

        // decoder.getModelDecoders().add(new TLcdSHPModelDecoder());

        // Decode city_125.shp to create an ILcdModel
        ILcdModel shpModel = decoder.decode(shpString);

        return shpModel;
    }

    private static ILcdModel createRasterModel() throws IOException {
        // This composite decoder can decode all supported formats
        ILcdModelDecoder decoder =
                new TLcdCompositeModelDecoder(TLcdServiceLoader.getInstance(ILcdModelDecoder.class));

        // Decode a sample data set (imagery data)
        ILcdModel geopackageModel = decoder.decode("Data/GeoPackage/bluemarble.gpkg");

        return geopackageModel;
    }

    private static ILspLayer createLayer(ILcdModel aModel) {
        TLspShapeLayerBuilder layerBuilder = TLspShapeLayerBuilder.newBuilder(ILspLayer.LayerType.REALTIME);
//                new TLspCompositeLayerFactory(TLcdServiceLoader.getInstance(ILspLayerFactory.class));

//        if (layerFactory.canCreateLayers(aModel)) {
//            Collection<ILspLayer> layers = layerFactory.createLayers(aModel);
//            //We only expect a single layer for our data
//            return layers.iterator().next();
//        }
        layerBuilder.model(aModel);
        ILspLayer layer = layerBuilder.build();
        if (layer != null) {
            return layer;
        }
        throw new RuntimeException("Could not create a layer for " + aModel.getModelDescriptor().getDisplayName());
    }

    static ILspLayer createGridLayer() {
        return TLspLonLatGridLayerBuilder.newBuilder().build();
    }

    private JComponent createLayerControl(ILspView aView) {
        return new TLcdLayerTree(aView);
    }

    static Action createSwitchTo2DAction(ILspView aView) {
        AbstractAction action = new AbstractAction("2D") {
            @Override
            public void actionPerformed(ActionEvent e) {
                TLspViewTransformationUtil.setup2DView(
                        aView,
                        new TLcdGridReference(new TLcdGeodeticDatum(),
                                new TLcdEquidistantCylindrical()),
                        true
                );
            }
        };
        action.putValue(Action.SHORT_DESCRIPTION, "Switch the view to 2D");
        return action;
    }

    private Action createSwitchTo3DAction(ILspView aView) {
        AbstractAction action = new AbstractAction("3D") {
            @Override
            public void actionPerformed(ActionEvent e) {
                TLspViewTransformationUtil.setup3DView(aView, true);
            }
        };
        action.putValue(Action.SHORT_DESCRIPTION, "Switch the view to 3D");
        return action;
    }

    public static void main(String[] args) {
        //Swing components must be created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new MainApplicationGPUSHP().createUI();
            frame.setVisible(true);
        });
    }
}
