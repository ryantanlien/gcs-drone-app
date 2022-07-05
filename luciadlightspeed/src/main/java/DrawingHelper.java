import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import com.luciad.format.object3d.obj.TLcdOBJMeshDecoder;
import com.luciad.format.object3d.openflight.TLcdOpenFlightFileDecoder;
import com.luciad.format.object3d.openflight.lightspeed.TLspOpenFlight3DIcon;
import com.luciad.format.object3d.openflight.model.TLcdOpenFlightHeaderNode;
import com.luciad.geodesy.ILcdEllipsoid;
import com.luciad.geodesy.ILcdGeodeticDatum;
import com.luciad.geodesy.TLcdGeodeticDatum;
import com.luciad.gui.ILcdIcon;
import com.luciad.gui.TLcdHaloIcon;
import com.luciad.gui.TLcdImageIcon;
import com.luciad.io.TLcdInputStreamFactory;
import com.luciad.model.ILcdModel;
import com.luciad.model.TLcdVectorModel;
import com.luciad.reference.TLcdGeodeticReference;
import com.luciad.view.lightspeed.TLspSwingView;
import com.luciad.view.lightspeed.layer.ILspInteractivePaintableLayer;
import com.luciad.view.lightspeed.layer.ILspLayer;
import com.luciad.view.lightspeed.layer.TLspPaintRepresentationState;
import com.luciad.view.lightspeed.layer.TLspPaintState;
import com.luciad.view.lightspeed.layer.shape.TLspShapeLayerBuilder;
import com.luciad.view.lightspeed.painter.mesh.ILsp3DIcon;
import com.luciad.view.lightspeed.painter.mesh.TLspMesh3DIcon;
import com.luciad.view.lightspeed.style.ALspStyle;
import com.luciad.view.lightspeed.style.ILspWorldElevationStyle.ElevationMode;
import com.luciad.view.lightspeed.style.TLsp3DIconStyle;
import com.luciad.view.lightspeed.style.TLspIconStyle;
import com.luciad.view.lightspeed.style.TLspIconStyle.ScalingMode;
import com.luciad.view.lightspeed.style.styler.TLspEditableStyler;
import com.luciad.view.lightspeed.style.styler.TLspLabelStyler;

public class DrawingHelper {
	private TLspSwingView view;
	private ILcdGeodeticDatum datum = new TLcdGeodeticDatum();
	private ILcdEllipsoid ellipsoid = datum.getEllipsoid();
	private double uom = Math.toRadians(ellipsoid.getA());
	private ILspLayer lspLayer;
	public DrawingHelper(TLspSwingView view) {
		this.view = view;
		initDrawingLayer();
	}
	
	private void initDrawingLayer(){
		TLspShapeLayerBuilder layerBuilder = TLspShapeLayerBuilder.newBuilder();

		TLspEditableStyler mainStyler = new TLspEditableStyler();
		TLspEditableStyler selStyler = new TLspEditableStyler();
		TLcdVectorModel model = new TLcdVectorModel();
		TLcdGeodeticDatum datum = new TLcdGeodeticDatum();
		model.setModelReference(new TLcdGeodeticReference(datum));
		
		TLspLabelStyler labelStyler = TLspLabelStyler.newBuilder().build();
		
		
		lspLayer = layerBuilder.model(model)
				.bodyStyler(TLspPaintState.REGULAR, mainStyler).labelStyler(TLspPaintState.REGULAR, labelStyler).labelStyler(TLspPaintState.SELECTED, labelStyler)
				.bodyStyler(TLspPaintState.SELECTED, selStyler).build();
		view.addLayer(lspLayer);
	}
	public ILspLayer getDrawingLayer(){
		return lspLayer;
	}
	public ALspStyle createIconStyle(BufferedImage bufferedImage, boolean isUseOrientation ,boolean isHalo, double _3DScale, String _3DPath, boolean is3D) {
		ALspStyle iconStyle = null;
		ILsp3DIcon icon = null;
		double scale = 1;
		if (_3DPath != null) {
			icon = loadIcon(_3DPath);
			if (is3D) {
				scale = _3DScale;
			} else {
				scale = scale / uom;
			}
			if (icon == null) {
				iconStyle = TLsp3DIconStyle.newBuilder().icon(_3DPath).scale(scale)
						.iconSizeMode(TLsp3DIconStyle.ScalingMode.SCALE_FACTOR)
						.elevationMode(ElevationMode.OBJECT_DEPENDENT).recenterIcon(false).build();
			} else {
				iconStyle = TLsp3DIconStyle.newBuilder().icon(icon).scale(scale)
						.iconSizeMode(TLsp3DIconStyle.ScalingMode.SCALE_FACTOR)
						.elevationMode(ElevationMode.OBJECT_DEPENDENT).recenterIcon(false).build();
			}

		} else {
			ILcdIcon imageIcon = new TLcdImageIcon(bufferedImage);
			if (isHalo) {
				imageIcon = new TLcdHaloIcon(imageIcon, Color.WHITE, 2);

			}
			iconStyle = TLspIconStyle.newBuilder().icon(imageIcon)
					// Set icons to have fixed view coordinates
					// .scalingMode(ScalingMode.VIEW_SCALING)
					.scalingMode(ScalingMode.VIEW_SCALING).useOrientation(isUseOrientation).zOrder(1)
					.scale(0.05)
					// .worldSize(WORLD_SIZE_1)
					.elevationMode(ElevationMode.OBJECT_DEPENDENT)
					// Set the icons' alpha value
					.opacity(1.0f).build();
		}

		return iconStyle;
	}

	public void styleElement(ALspStyle iconStyle, ILspInteractivePaintableLayer lspLayer,
			OrientationLonLatHeightPointModel imageShape) {
		TLspEditableStyler mainStyler = (TLspEditableStyler) lspLayer
				.getStyler(TLspPaintRepresentationState.REGULAR_BODY);
		TLspEditableStyler selectStyler = (TLspEditableStyler) lspLayer
				.getStyler(TLspPaintRepresentationState.SELECTED_BODY);
		
		TLspIconStyle selIconStyle = TLspIconStyle.newBuilder()
				.icon(new TLcdImageIcon("luciadlightspeed\\src\\main\\resources\\images\\box-outline-red.png")).zOrder(0).scale(0.15).build();
		
		
		mainStyler.setStyle(lspLayer.getModel(), imageShape,
				Arrays.<ALspStyle> asList(iconStyle));
		selectStyler.setStyle(lspLayer.getModel(), imageShape,
				Arrays.<ALspStyle> asList(iconStyle,selIconStyle));
	}

	public void addOrUpdateElement(OrientationLonLatHeightPointModel imageShape, double lon, double lat, double alt, double yaw, double pitch, double roll,
                                   ILspInteractivePaintableLayer lspLayer, boolean isNew) {
		imageShape.setOrientation(yaw);
		imageShape.setPitch(pitch);
		imageShape.setRoll(roll);

		imageShape.move3D(lon,lat,alt);

		if (!isNew) {
			lspLayer.getModel().elementChanged(imageShape, ILcdModel.FIRE_NOW);
		} else {
			lspLayer.getModel().addElement(imageShape, ILcdModel.FIRE_NOW);
		}

	}

	private static ILsp3DIcon loadIcon(String aIconSource) {
		try {
			TLcdInputStreamFactory fInputStreamFactory = new TLcdInputStreamFactory();

			String extension = aIconSource.substring(aIconSource.length() - 4);
			if ((".obj").equalsIgnoreCase(extension)) {
				TLcdOBJMeshDecoder decoder = new TLcdOBJMeshDecoder();
				decoder.setInputStreamFactory(fInputStreamFactory);
				TLspMesh3DIcon icon = new TLspMesh3DIcon(decoder.decodeMesh(aIconSource));
				// return new TLspBounds3DIcon(icon.getBounds());
				return icon;
			} else if ((".flt").equalsIgnoreCase(extension)) {
				TLcdOpenFlightFileDecoder decoder = new TLcdOpenFlightFileDecoder();
				decoder.setInputStreamFactory(fInputStreamFactory);
				TLcdOpenFlightHeaderNode scene = decoder.decode(aIconSource);
				return new TLspOpenFlight3DIcon(scene);
			} else if ((".dae").equalsIgnoreCase(extension)) {
				return null;
				// throw new IllegalArgumentException("Could not load icon,
				// unsupported file format: " + extension);
			} else {
				throw new IllegalArgumentException("Could not load icon, unsupported file format: " + extension);
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Could not load icon", e);
		}
	}

}
