import com.luciad.format.shp.TLcdSHPModelDecoder;
import com.luciad.model.ILcdModel;
import com.luciad.view.lightspeed.TLspSwingView;
import com.luciad.view.lightspeed.layer.ILspLayer;
import com.luciad.view.lightspeed.layer.shape.TLspShapeLayerBuilder;
import com.luciad.view.lightspeed.painter.grid.TLspLonLatGridLayerBuilder;
import dvd.gcs.app.luciadlightspeed.LuciadMapInterface;
import javafx.embed.swing.SwingNode;
import javafx.scene.image.Image;
import org.pf4j.Extension;

import java.io.IOException;

// @Extension
public class LuciadMap implements LuciadMapInterface {
    public static final String[] shpStrings = { // order of strings matters
            "singapore-msia-brunei/gis_osm_landuse_a_free_1.shp",
            "singapore-msia-brunei/gis_osm_pois_a_free_1.shp",
            "singapore-msia-brunei/gis_osm_pofw_a_free_1.shp",
            "singapore-msia-brunei/gis_osm_natural_a_free_1.shp",
            "singapore/SGP_adm0.shp",
            "singapore-msia-brunei/gis_osm_buildings_a_free_1.shp",
            "singapore-msia-brunei/gis_osm_railways_free_1.shp",
            "singapore-msia-brunei/gis_osm_roads_free_1.shp",
    };
    public static final String[] unusedShpStrings = {
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
    private SwingNode swingNode;
    private TLspSwingView view;

    public LuciadMap() {
        this.swingNode = createSwingNode();
    }

    private SwingNode createSwingNode() {
        this.view = createView();
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(view.getHostComponent());
//        frame.add(view.getHostComponent(), BorderLayout.CENTER);

        addData(view);

        // TODO: need layer control?
//        JComponent layerControl = createLayerControl(view);
//        frame.add(layerControl, BorderLayout.EAST);

        view.addLayer(createGridLayer());

//        frame.setSize(2000, 1500);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return swingNode;
    }

    private TLspSwingView createView() {
        // return TLspViewBuilder.newBuilder().buildSwingView();
        return new TLspSwingView();
    }

    private ILcdModel createSHPModel(String shpString) throws IOException {
        // Use specific decoder
        TLcdSHPModelDecoder decoder = new TLcdSHPModelDecoder();

        ILcdModel shpModel = decoder.decode(shpString);

        return shpModel;
    }

    private ILspLayer createLayer(ILcdModel aModel) {
        // Use specific layer builder
        TLspShapeLayerBuilder layerBuilder = TLspShapeLayerBuilder.newBuilder(ILspLayer.LayerType.REALTIME);

        layerBuilder.model(aModel);
        ILspLayer layer = layerBuilder.build();
        if (layer != null) {
            return layer;
        }
        throw new RuntimeException("Could not create a layer for " + aModel.getModelDescriptor().getDisplayName());
    }

    private void addData(TLspSwingView view) {
        try {
            for (String shpString : shpStrings) {
                ILcdModel shpModel = createSHPModel(shpString);
                view.addLayer(createLayer(shpModel));
            }

        } catch (IOException e) {
            throw new RuntimeException("Problem during data decoding", e);
        }
    }

    private ILspLayer createGridLayer() {
        return TLspLonLatGridLayerBuilder.newBuilder().build();
    }

//    private JComponent createLayerControl(ILspView aView) {
//        return new TLcdLayerTree(aView);
//    }

    @Override
    public SwingNode getSwingNode() {
        return swingNode;
    }

    @Override
    public void addOrUpdateElement(String id, double lat, double lon, double height, boolean isNew) {
        // TODO: add drone to map
    }
}

