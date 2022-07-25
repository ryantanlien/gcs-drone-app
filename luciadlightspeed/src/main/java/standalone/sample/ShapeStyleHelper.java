package standalone.sample;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import com.luciad.shape.ILcdShape;
import com.luciad.view.lightspeed.layer.ILspInteractivePaintableLayer;
import com.luciad.view.lightspeed.layer.ILspLayer;
import com.luciad.view.lightspeed.layer.TLspPaintRepresentationState;
import com.luciad.view.lightspeed.style.ILspWorldElevationStyle.ElevationMode;
import com.luciad.view.lightspeed.style.TLspLineStyle;
import com.luciad.view.lightspeed.style.TLspLineStyle.DashPattern;
import com.luciad.view.lightspeed.style.TLspVerticalLineStyle;
import com.luciad.view.lightspeed.style.TLspViewDisplacementStyle;
import com.luciad.view.lightspeed.style.styler.TLspEditableStyler;

public class ShapeStyleHelper {

	private static final String MSG_PREFIX = "@GIS:";

	private static final float STYLER_OPACITY = 1f;

	public static void applyStyleToShape(ILcdShape shape, ILspLayer aLayer) {
		System.err.println("STLING");
		ElevationMode elevationMode = ElevationMode.OBJECT_DEPENDENT;

		// TLspLabelStyler labelStyler = null;

		ILspInteractivePaintableLayer lspLayer = (ILspInteractivePaintableLayer) aLayer;
		TLspEditableStyler mainStyler = (TLspEditableStyler) lspLayer
				.getStyler(TLspPaintRepresentationState.REGULAR_BODY);
		TLspEditableStyler selStyler = (TLspEditableStyler) lspLayer
				.getStyler(TLspPaintRepresentationState.SELECTED_BODY);
		TLspEditableStyler editStyler = (TLspEditableStyler) lspLayer
				.getStyler(TLspPaintRepresentationState.EDITED_BODY);

		// TLspEditableStyler mainLabelStyler = (TLspEditableStyler)
		// lspLayer.getStyler(TLspPaintRepresentationState.)

		List<com.luciad.view.lightspeed.style.ALspStyle> mainStyleList = new ArrayList<com.luciad.view.lightspeed.style.ALspStyle>();
		List<com.luciad.view.lightspeed.style.ALspStyle> selStyleList = new ArrayList<com.luciad.view.lightspeed.style.ALspStyle>();
		List<com.luciad.view.lightspeed.style.ALspStyle> editStyleList = new ArrayList<com.luciad.view.lightspeed.style.ALspStyle>();

		DashPattern dashPattern;
		int SOLID_SCALE = 0;
		int DASH_SCALE = 10;

		// if 0 means solid
		dashPattern = new TLspLineStyle.DashPattern(DashPattern.SOLID, SOLID_SCALE);

		com.luciad.view.lightspeed.style.ALspStyle lineStyle = TLspLineStyle.newBuilder().color(Color.RED).width(5)
				.elevationMode(elevationMode).opacity(1).zOrder(1).build();

		com.luciad.view.lightspeed.style.ALspStyle selLineStyle = TLspLineStyle.newBuilder().color(Color.white).width(2)
				.dashPattern(dashPattern).width(1).elevationMode(elevationMode).opacity(1).build();

		com.luciad.view.lightspeed.style.ALspStyle vertLineStyle = TLspVerticalLineStyle.newBuilder().color(Color.white)
				.opacity(STYLER_OPACITY).build();

		// labelStyler =
		// TLspLabelStyler.newBuilder().styles(textStyle).build();

		mainStyleList.add(lineStyle);
		mainStyleList.add(vertLineStyle);

		mainStyleList.add(TLspViewDisplacementStyle.newBuilder().build());

		selStyleList.add(selLineStyle);

		editStyleList.add(lineStyle);
		editStyleList.add(selLineStyle);
		
		mainStyler.setStyle(lspLayer.getModel(), shape, mainStyleList);
		selStyler.setStyle(lspLayer.getModel(), shape, selStyleList);
		editStyler.setStyle(lspLayer.getModel(), shape, editStyleList);
		
	}


}
