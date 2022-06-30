package standalone;

import com.luciad.model.ILcdModel;
import com.luciad.model.ILcdModelDecoder;
import com.luciad.model.TLcdCompositeModelDecoder;
import com.luciad.util.service.TLcdServiceLoader;
import com.luciad.view.gxy.ILcdGXYLayer;
import com.luciad.view.gxy.ILcdGXYLayerFactory;
import com.luciad.view.gxy.ILcdGXYView;
import com.luciad.view.gxy.TLcdCompositeGXYLayerFactory;
import com.luciad.view.map.TLcdMapJPanel;
import com.luciad.view.swing.TLcdLayerTree;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainApplication {
    public static final String shapeFilePath = "singapore/SGP_adm0.shp";

    public JFrame createUI() {
        JFrame frame = new JFrame("Demo GXY application");

        TLcdMapJPanel view = createView();
        frame.add(view, BorderLayout.CENTER);
        try {
//          ILcdGXYLayer backgroundLayer = createLayer(createRasterModel());
//          view.addGXYLayer(backgroundLayer);

            ILcdGXYLayer citiesLayer = createLayer(createSHPModel());
            view.addGXYLayer(citiesLayer);
        } catch (IOException e) {
            throw new RuntimeException("Problem during data decoding:", e);
        }

        //Move the grid layer to the top of the view,
        //so that it is rendered on top of the other layers
        view.moveLayerAt(view.layerCount() - 1, view.getGridLayer());

        JComponent layerControl = createLayerControl(view);
        frame.add(layerControl, BorderLayout.EAST);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return frame;
    }

    private TLcdMapJPanel createView() {
        TLcdMapJPanel view = new TLcdMapJPanel();

        TLcdCompositeGXYLayerFactory layerFactory =
                new TLcdCompositeGXYLayerFactory(TLcdServiceLoader.getInstance(ILcdGXYLayerFactory.class));

        //Install the layer factory on the view
        //When adding models to the view, this factory is used to create layers for those models
        view.setGXYLayerFactory(layerFactory);

        return view;
    }

    private ILcdModel createSHPModel() throws IOException {
        // This composite decoder can decode all supported formats
        ILcdModelDecoder decoder =
                new TLcdCompositeModelDecoder(TLcdServiceLoader.getInstance(ILcdModelDecoder.class));

        // Decode .shp to create an ILcdModel
        ILcdModel shpModel = decoder.decode(shapeFilePath);

        return shpModel;
    }

//    private ILcdModel createRasterModel() throws IOException {
//        // This composite decoder can decode all supported formats
//        ILcdModelDecoder decoder =
//                new TLcdCompositeModelDecoder(TLcdServiceLoader.getInstance(ILcdModelDecoder.class));
//
//        // Decode a sample data set (imagery data)
//        ILcdModel geopackageModel = decoder.decode("Data/GeoPackage/bluemarble.gpkg");
//
//        return geopackageModel;
//    }

    private ILcdGXYLayer createLayer(ILcdModel aModel) {
        TLcdCompositeGXYLayerFactory layerFactory =
                new TLcdCompositeGXYLayerFactory(TLcdServiceLoader.getInstance(ILcdGXYLayerFactory.class));

        ILcdGXYLayer layer = layerFactory.createGXYLayer(aModel);
        if (layer != null) {
            return layer;
        }
        throw new RuntimeException("Could not create a layer for " + aModel.getModelDescriptor().getDisplayName());
    }

    private JComponent createLayerControl(ILcdGXYView aView) {
        return new TLcdLayerTree(aView);
    }

    public static void main(String[] args) {
        //Swing components must be created on the Event Dispatch Thread
        EventQueue.invokeLater(() -> {
            JFrame frame = new MainApplication().createUI();
            frame.setVisible(true);
        });
    }
}
