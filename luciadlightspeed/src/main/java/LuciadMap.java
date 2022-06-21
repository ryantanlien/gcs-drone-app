import com.luciad.format.shp.TLcdSHPModelDecoder;
import com.luciad.model.ILcdModel;
import com.luciad.model.ILcdModelDecoder;
import com.luciad.model.TLcdCompositeModelDecoder;
import com.luciad.util.service.TLcdServiceLoader;
import com.luciad.view.lightspeed.ILspView;
import com.luciad.view.lightspeed.TLspSwingView;
import com.luciad.view.lightspeed.TLspViewBuilder;
import com.luciad.view.lightspeed.layer.ILspLayer;
import com.luciad.view.lightspeed.layer.ILspLayerFactory;
import com.luciad.view.lightspeed.layer.TLspCompositeLayerFactory;
import com.luciad.view.lightspeed.layer.shape.TLspShapeLayerBuilder;
import com.luciad.view.lightspeed.painter.grid.TLspLonLatGridLayerBuilder;
import com.luciad.view.swing.TLcdLayerTree;
import dvd.gcs.app.luciadlightspeed.LuciadMapInterface;
import javafx.embed.swing.SwingNode;
import javafx.scene.image.Image;
import org.pf4j.Extension;

import javax.swing.*;
import java.io.IOException;
import java.util.Collection;

@Extension
public class LuciadMap implements LuciadMapInterface {
    private SwingNode swingNode;
    private TLspSwingView view;

    public LuciadMap() {
        this.view = createView();
        this.swingNode = new SwingNode();

        swingNode.setContent(view.getHostComponent());
//        frame.add(view.getHostComponent(), BorderLayout.CENTER);

        addData(view);

        // TODO: need layer control?
        JComponent layerControl = createLayerControl(view);
//        frame.add(layerControl, BorderLayout.EAST);

        view.addLayer(createGridLayer());

//        frame.setSize(2000, 1500);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public TLspSwingView createView() {
        return TLspViewBuilder.newBuilder().buildSwingView();
    }

    private static ILcdModel createSHPModel() throws IOException {
        // Use specific decoder
        TLcdSHPModelDecoder decoder = new TLcdSHPModelDecoder();

        ILcdModel shpModel = decoder.decode("singapore-msia-brunei/gis_osm_natural_a_free_1.shp");

        return shpModel;
    }

    private static ILspLayer createLayer(ILcdModel aModel) {
        // Use specific layer builder
        TLspShapeLayerBuilder layerBuilder = TLspShapeLayerBuilder.newBuilder(ILspLayer.LayerType.REALTIME);

        layerBuilder.model(aModel);
        ILspLayer layer = layerBuilder.build();
        if (layer != null) {
            return layer;
        }
        throw new RuntimeException("Could not create a layer for " + aModel.getModelDescriptor().getDisplayName());
    }

    static void addData(TLspSwingView view) {
        try {
            ILcdModel shpModel = createSHPModel();
            view.addLayer(createLayer(shpModel));

        } catch (IOException e) {
            throw new RuntimeException("Problem during data decoding", e);
        }
    }

    static ILspLayer createGridLayer() {
        return TLspLonLatGridLayerBuilder.newBuilder().build();
    }

    private JComponent createLayerControl(ILspView aView) {
        return new TLcdLayerTree(aView);
    }

    @Override
    public SwingNode getSwingNode() {
        return swingNode;
    }
    @Override
    public void addOrUpdateElement(String id, double lat, double lon, double height, Image icon) {
        // TODO: add drone to map
    }
}

