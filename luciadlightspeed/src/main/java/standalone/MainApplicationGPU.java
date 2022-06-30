package standalone;

import com.luciad.geodesy.TLcdGeodeticDatum;
import com.luciad.model.ILcdModel;
import com.luciad.model.ILcdModelDecoder;
import com.luciad.model.TLcdCompositeModelDecoder;
import com.luciad.projection.TLcdEquidistantCylindrical;
import com.luciad.reference.TLcdGridReference;
import com.luciad.util.service.TLcdServiceLoader;
import com.luciad.view.lightspeed.ILspAWTView;
import com.luciad.view.lightspeed.ILspView;
import com.luciad.view.lightspeed.TLspViewBuilder;
import com.luciad.view.lightspeed.layer.ILspLayer;
import com.luciad.view.lightspeed.layer.ILspLayerFactory;
import com.luciad.view.lightspeed.layer.TLspCompositeLayerFactory;
import com.luciad.view.lightspeed.painter.grid.TLspLonLatGridLayerBuilder;
import com.luciad.view.lightspeed.util.TLspViewTransformationUtil;
import com.luciad.view.swing.TLcdLayerTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Collection;

public class MainApplicationGPU {
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

        frame.setSize(2000, 1500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    public ILspAWTView createView() {
        return TLspViewBuilder.newBuilder().buildAWTView();
    }

//    private TLspFXView createFxView() {
//        return TLspViewBuilder.newBuilder().buildFXView();
//    }

    static void addData(ILspView view) {
        try {
            ILcdModel shpModel = createSHPModel();
            view.addLayer(createLayer(shpModel));

//            ILcdModel rasterModel = createRasterModel();
//            view.addLayer(createLayer(rasterModel));
        } catch (IOException e) {
            throw new RuntimeException("Problem during data decoding", e);
        }
    }

    private static ILcdModel createSHPModel() throws IOException {
        // This composite decoder can decode all supported formats
        ILcdModelDecoder decoder =
                new TLcdCompositeModelDecoder(TLcdServiceLoader.getInstance(ILcdModelDecoder.class));

        // Decode city_125.shp to create an ILcdModel
        ILcdModel shpModel = decoder.decode("singapore-msia-brunei/gis_osm_transport_a_free_1.shp");

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
        TLspCompositeLayerFactory layerFactory =
                new TLspCompositeLayerFactory(TLcdServiceLoader.getInstance(ILspLayerFactory.class));

        if (layerFactory.canCreateLayers(aModel)) {
            Collection<ILspLayer> layers = layerFactory.createLayers(aModel);
            //We only expect a single layer for our data
            return layers.iterator().next();
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
        EventQueue.invokeLater(() -> {
            JFrame frame = new MainApplicationGPU().createUI();
            frame.setVisible(true);
        });
    }
}
