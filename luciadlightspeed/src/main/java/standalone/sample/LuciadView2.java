package standalone.sample;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.luciad.format.geojson.TLcdGeoJsonModelDecoder;
import com.luciad.format.geojson.TLcdGeoJsonModelDescriptor;
import com.luciad.format.raster.TLcdColorModelFactory;
import com.luciad.format.raster.TLcdDTEDModelDecoder;
import com.luciad.format.raster.TLcdDTEDModelDescriptor;
import com.luciad.format.raster.TLcdGeoTIFFModelDecoder;
import com.luciad.format.raster.TLcdGeoTIFFModelDescriptor;
import com.luciad.format.raster.TLcdMultilevelGeoTIFFModelDescriptor;
import com.luciad.format.shp.TLcdSHPModelDecoder;
import com.luciad.format.shp.TLcdSHPModelDescriptor;
import com.luciad.geodesy.TLcdGeodeticDatum;
import com.luciad.model.ILcdModel;
import com.luciad.model.ILcdModelDecoder;
import com.luciad.model.ILcdModelDescriptor;
import com.luciad.model.TLcd2DBoundsIndexedModel;
import com.luciad.model.TLcdCompositeModelDecoder;
import com.luciad.ogc.sld.model.TLcdSLDFeatureTypeStyle;
import com.luciad.ogc.sld.view.lightspeed.TLspSLDStyler;
import com.luciad.ogc.sld.xml.TLcdSLDFeatureTypeStyleDecoder;
import com.luciad.projection.TLcdGeodetic;
import com.luciad.reference.TLcdGeodeticReference;
import com.luciad.reference.TLcdGridReference;
import com.luciad.shape.ILcdPoint;
import com.luciad.shape.shape2D.TLcdLonLatBounds;
import com.luciad.shape.shape2D.TLcdLonLatPoint;
import com.luciad.shape.shape2D.TLcdXYPoint;
import com.luciad.shape.shape3D.TLcdXYZPoint;
import com.luciad.transformation.TLcdGeodetic2Geocentric;
import com.luciad.util.ILcdSelectionListener;
import com.luciad.util.TLcdOutOfBoundsException;
import com.luciad.util.TLcdSelectionChangedEvent;
import com.luciad.view.lightspeed.ILspView;
import com.luciad.view.lightspeed.TLspSwingView;
import com.luciad.view.lightspeed.TLspViewBuilder;
import com.luciad.view.lightspeed.camera.ALspViewXYZWorldTransformation;
import com.luciad.view.lightspeed.camera.TLspViewXYZWorldTransformation3D;
import com.luciad.view.lightspeed.layer.ILspInteractivePaintableLayer;
import com.luciad.view.lightspeed.layer.ILspLayer;
import com.luciad.view.lightspeed.layer.ILspLayer.LayerType;
import com.luciad.view.lightspeed.layer.TLspCompositeLayerFactory;
import com.luciad.view.lightspeed.layer.TLspLayerTreeNode;
import com.luciad.view.lightspeed.layer.TLspPaintRepresentationState;
import com.luciad.view.lightspeed.layer.TLspPaintState;
import com.luciad.view.lightspeed.layer.raster.TLspRasterLayerBuilder;
import com.luciad.view.lightspeed.layer.shape.TLspShapeLayerBuilder;
import com.luciad.view.lightspeed.style.ALspStyle;
import com.luciad.view.lightspeed.style.TLspRasterStyle;
import com.luciad.view.lightspeed.util.TLspViewNavigationUtil;

import javafx.embed.swing.SwingNode;

public class LuciadView2 {
	private TLspViewNavigationUtil naviUtil;

	private static final double BORDER_FACTOR = 0.5;
	private static final double RESOLUTION_FACTOR = 200000.0;
	private RasterStyler mapRasterStyler = new RasterStyler(
			TLspRasterStyle.newBuilder().startResolutionFactor(RESOLUTION_FACTOR).build());
	private RasterStyler elevationStyler = new RasterStyler(
			TLspRasterStyle.newBuilder().opacity(0).colorMap(TLcdColorModelFactory.createElevationColorMap()).build());
	TLspSwingView view = null;
	private TLspLayerTreeNode mapLayers = new TLspLayerTreeNode("mapLayers");
	private HashMap<ILcdModel, TLspSLDStyler> sldStylerHashMap = new HashMap<ILcdModel, TLspSLDStyler>();
	private HashMap<String, ILcdModel> modelHashMap = new HashMap<>();

	private TLcdCompositeModelDecoder compositeModelDecoder;
	private DrawingHelper drawingHelper;

	public LuciadView2() throws Exception {
		view = TLspViewBuilder.newBuilder().viewType(ILspView.ViewType.VIEW_2D).buildSwingView();
		TLcdGeodeticDatum datum = new TLcdGeodeticDatum();
		double uom = Math.toRadians(datum.getEllipsoid().getA());
		TLcdGeodetic projection = new TLcdGeodetic(uom, uom);
		view.setXYZWorldReference(new TLcdGridReference(datum, projection, 0.0, 0.0, 1.0, uom, 0.0));
		naviUtil = new TLspViewNavigationUtil(view);
		initCompositeModelDecoder();
//		initShpFiles();
//		initShpFilesWithoutSld();
		initMapLayer();
		zoomTo(60000);
		centerAt(103.684030, 1.4216877);

		view.addLayer(mapLayers);
		view.addLayerSelectionListener(new ILcdSelectionListener() {

			@Override
			public void selectionChanged(TLcdSelectionChangedEvent arg0) {
				System.err.println(arg0.getSelection().getSelectedObjects());
			}

		});
		
		drawingHelper = new DrawingHelper(view);
		new ShapeDrawingHelper(view);

		ALspStyle iconStyle = drawingHelper.createIconStyle(loadImage("resource\\drone.png"), true, true, 0, null,
				false);
		OrientationLonLatHeightPointModel imageShape = new OrientationLonLatHeightPointModel("Drone 1");
		drawingHelper.styleElement(iconStyle, (ILspInteractivePaintableLayer) drawingHelper.getDrawingLayer(),
				imageShape);
		drawingHelper.addOrUpdateElement(imageShape, 103.684030, 1.4216877, 0, 0, 0, 0,
				(ILspInteractivePaintableLayer) drawingHelper.getDrawingLayer(), true);

		Thread t = new Thread(() -> {
			try {
				while (true) {
					drawingHelper.addOrUpdateElement(imageShape, imageShape.getLon() + 0.000005, imageShape.getLat(), 0,
							imageShape.getOrientation() + 15, 0, 0,
							(ILspInteractivePaintableLayer) drawingHelper.getDrawingLayer(), false);
					Thread.sleep(200);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		});
		t.start();
		
	}

	private static BufferedImage loadImage(String path) {
		BufferedImage img = null;

		try {
			img = ImageIO.read(new File(path));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return img;
	}

	private void initMapLayer() {
		for (ILcdModel model : modelHashMap.values()) {
			ILspLayer mapLayer = createMapLayer(model);
			mapLayers.addLayer(mapLayer);
		}
	}

	private void initCompositeModelDecoder() {
		compositeModelDecoder = new TLcdCompositeModelDecoder();
		List<ILcdModelDecoder> modelDecoders = compositeModelDecoder.getModelDecoders();

		modelDecoders.add(new TLcdGeoTIFFModelDecoder());
		modelDecoders.add(new TLcdSHPModelDecoder());
		modelDecoders.add(new TLcdDTEDModelDecoder());
		modelDecoders.add(new TLcdGeoJsonModelDecoder());
	}

	private void initShpFiles() {
		File mapDirectory = new File("map\\shp");
		File[] fileArr = mapDirectory.listFiles();
		for (File file : fileArr) {
			String[] strArr = file.getName().split("\\.");
			if (strArr.length != 2) {
				continue;
			}
			String name = strArr[0];
			String ext = strArr[1];
			if ("sld".equalsIgnoreCase(ext)) {
				if (modelHashMap.containsKey(name)) {
					try {
						sldStylerHashMap.put(modelHashMap.get(name), loadSLDStyle(file.getAbsolutePath()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if ("shp".equalsIgnoreCase(ext)) {
				ILcdModel mapModel;
				try {
					mapModel = compositeModelDecoder.decode(file.getAbsolutePath());
					modelHashMap.put(name, mapModel);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static TLspSLDStyler loadSLDStyle(String styleLocation) throws IOException {

		File styleDir = new File(styleLocation);
		String stylePath = styleDir.getAbsolutePath();
		stylePath = stylePath.replace("\\", "\\\\");

		TLcdSLDFeatureTypeStyleDecoder sldDecoder = new TLcdSLDFeatureTypeStyleDecoder();

		TLcdSLDFeatureTypeStyle style = sldDecoder.decodeFeatureTypeStyle(stylePath);

		TLspSLDStyler styler = new TLspSLDStyler(style);

		return styler;
	}

	public SwingNode getMapSwingNode() {
		SwingNode result = new SwingNode();
		JPanel mapPanel = new JPanel();
		mapPanel.setLayout(new BorderLayout());
		mapPanel.add(view.getHostComponent(), BorderLayout.CENTER);

		result.setContent(mapPanel);
		FXTouchEventDispatcher.install(result);
		mapPanel.validate();

		return result;
	}

	public JComponent getJComponent() {

		return view.getHostComponent();
	}

	private ILspLayer createMapLayer(ILcdModel mapModel) {

		ILspLayer mapLayer = null;

		ILcdModelDescriptor modelDescriptor = mapModel.getModelDescriptor();

		if (modelDescriptor instanceof TLcdSHPModelDescriptor
				|| modelDescriptor instanceof TLcdGeoJsonModelDescriptor) {
			// SHPModel and GeoJsonModel
			TLcd2DBoundsIndexedModel shpModel = (TLcd2DBoundsIndexedModel) mapModel;

			TLspSLDStyler sldStyler = sldStylerHashMap.get(mapModel);

			if (sldStyler == null) {
				mapLayer = TLspShapeLayerBuilder.newBuilder().model(shpModel).layerType(ILspLayer.LayerType.BACKGROUND)
						.build();
			} else {
				mapLayer = TLspShapeLayerBuilder.newBuilder().model(shpModel).layerType(ILspLayer.LayerType.BACKGROUND)
						.bodyStyler(TLspPaintState.REGULAR, sldStyler).labelStyler(TLspPaintState.REGULAR, sldStyler)
						.build();
			}

		} else if (modelDescriptor instanceof TLcdDTEDModelDescriptor) {
			// DTEDModel
			mapLayer = TLspRasterLayerBuilder.newBuilder().model(mapModel).layerType(ILspLayer.LayerType.BACKGROUND)
					.styler(TLspPaintRepresentationState.REGULAR_BODY, elevationStyler).build();

		} else if (modelDescriptor instanceof TLcdGeoTIFFModelDescriptor
				|| modelDescriptor instanceof TLcdMultilevelGeoTIFFModelDescriptor) {
			// GeoTIFFModel
			mapLayer = TLspRasterLayerBuilder.newBuilder()
					.styler(TLspPaintRepresentationState.REGULAR_BODY, mapRasterStyler).model(mapModel)
					.layerType(LayerType.BACKGROUND).borderFactor(BORDER_FACTOR).build();
		}

		return mapLayer;
	}

	public void centerAt(double lon, double lat) {
		if (view.getViewXYZWorldTransformation() instanceof TLspViewXYZWorldTransformation3D) {
			try {
				TLcdGeodeticDatum datum = new TLcdGeodeticDatum();
				TLcdGeodetic2Geocentric w2m = new TLcdGeodetic2Geocentric(new TLcdGeodeticReference(datum),
						view.getXYZWorldReference());
				TLcdXYPoint modelPoint = new TLcdXYPoint(lon, lat);
				TLcdXYZPoint worldPoint = new TLcdXYZPoint();
				w2m.modelPoint2worldSFCT(modelPoint, worldPoint);

				ALspViewXYZWorldTransformation w2v = view.getViewXYZWorldTransformation();
				TLcdXYZPoint viewPoint = new TLcdXYZPoint();
				w2v.worldPoint2ViewSFCT(worldPoint, viewPoint);

				naviUtil.center(new TLcdXYZPoint(worldPoint), null);

			} catch (TLcdOutOfBoundsException e) {
				e.printStackTrace();
			}
		} else {
			ILcdPoint lcdPoint = new TLcdLonLatPoint(lon, lat);

			try {
				naviUtil.center(new TLcdLonLatBounds(lcdPoint), null);

			} catch (TLcdOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
	}

	public void zoomTo(double scale) {
		naviUtil.zoom(scale / view.getViewXYZWorldTransformation().getScale());
	}

	private void initShpFilesWithoutSld() {
		TLcdSHPModelDecoder shpModelDecoder = new TLcdSHPModelDecoder();

		try {
			ILcdModel fModel = shpModelDecoder.decode("C://general-workspace/luciad-test/map/shp-other/test.shp");

			TLspCompositeLayerFactory factory = new TLspCompositeLayerFactory();
			Collection<ILspLayer> layers = factory.createLayers(fModel);
			ILspLayer fLayer = layers.iterator().next();
			view.addLayer(fLayer);
		} catch (IOException e) {
			throw new RuntimeException("Could not decode ", e);
		}
	}

}
