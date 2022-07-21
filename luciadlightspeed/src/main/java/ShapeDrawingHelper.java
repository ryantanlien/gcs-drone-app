import com.luciad.geodesy.ILcdGeodeticDatum;
import com.luciad.geodesy.TLcdGeodeticDatum;
import com.luciad.gui.ILcdAction;
import com.luciad.model.TLcdVectorModel;
import com.luciad.reference.TLcdGeodeticReference;
import com.luciad.util.ILcdFireEventMode;
import com.luciad.view.animation.ALcdAnimationManager;
import com.luciad.view.lightspeed.TLspSwingView;
import com.luciad.view.lightspeed.action.TLspSetControllerAction;
import com.luciad.view.lightspeed.controller.ILspController;
import com.luciad.view.lightspeed.controller.manipulation.ALspCreateControllerModel;
import com.luciad.view.lightspeed.controller.manipulation.TLspCreateController;
import com.luciad.view.lightspeed.editor.ELspCreationMode;
import com.luciad.view.lightspeed.editor.TLspCompositeEditor;
import com.luciad.view.lightspeed.editor.TLspShapeEditor;
import com.luciad.view.lightspeed.layer.ILspLayer;
import com.luciad.view.lightspeed.layer.TLspPaintState;
import com.luciad.view.lightspeed.layer.shape.TLspShapeLayerBuilder;
import com.luciad.view.lightspeed.painter.shape.TLspShapePainter;
import com.luciad.view.lightspeed.style.styler.TLspEditableStyler;
import standalone.sample.CreateControllerModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class ShapeDrawingHelper {
	private TLspSwingView view;
	private ILcdGeodeticDatum datum = new TLcdGeodeticDatum();
	private ILspLayer lspLayer;
	private ILspController defaultController;
	private TLspCreateController createController;

	public ShapeDrawingHelper(TLspSwingView view) {
		this.view = view;
		this.defaultController = view.getController();
		initDrawingLayer();
//		startShapeDrawing();

	}

	public void clearDrawing() {
		lspLayer.getModel().removeAllElements(ILcdFireEventMode.NO_EVENT);
	}

	public void startShapeDrawing() {
		clearDrawing();

		ALspCreateControllerModel createControllerModel = new CreateControllerModel(lspLayer);
		
		this.createController = new TLspCreateController(createControllerModel);

		createController.setActionToTriggerAfterCommit(new ILcdAction() {
			TLspSetControllerAction action = new TLspSetControllerAction(view, createController);
			private boolean isEnabled = true;

			@Override
			public void addPropertyChangeListener(PropertyChangeListener arg0) {
			}

			@Override
			public void removePropertyChangeListener(PropertyChangeListener arg0) {
			}

			@Override
			public void actionPerformed(ActionEvent arg0) {
				this.setEnabled(false);

				SwingUtilities.invokeLater(() -> {
					action.actionPerformed(arg0);
					
					if (arg0.getActionCommand().equals("creationFinished")) {
						createController.cancel();
						lspLayer.clearSelection(ILcdFireEventMode.FIRE_NOW);
						view.setController(defaultController);
					}
				});
			}

			@Override
			public Object getValue(String arg0) {
				return null;
			}

			@Override
			public boolean isEnabled() {
				return isEnabled;
			}

			@Override
			public void putValue(String arg0, Object arg1) {
			}

			@Override
			public void setEnabled(boolean isEnabled) {
				this.isEnabled = isEnabled;
			}
		});
	    ALcdAnimationManager.getInstance().removeAnimation(view.getViewXYZWorldTransformation());
		view.setController(createController);
	}

	private void initDrawingLayer() {
		// initialize the model
		TLcdVectorModel model = new TLcdVectorModel();
		TLcdGeodeticDatum datum = new TLcdGeodeticDatum();
		model.setModelReference(new TLcdGeodeticReference(datum));

		TLspEditableStyler mainStyler = new TLspEditableStyler();
		TLspEditableStyler selStyler = new TLspEditableStyler();

		// initialize layer
		TLspShapeLayerBuilder layerBuilder = TLspShapeLayerBuilder.newBuilder();

		TLspShapePainter painter = new TLspShapePainter();
		TLspCompositeEditor compositeEditor = new TLspCompositeEditor();
		TLspShapeEditor shapeEditor = new TLspShapeEditor();
		shapeEditor.setCreationMode(ELspCreationMode.CLICK);
		compositeEditor.addEditor(shapeEditor);

		lspLayer = layerBuilder.model(model).snapTarget(false).selectable(true).editableSupported(true)
				.bodyEditable(true).bodyPainter(painter).bodyEditor(compositeEditor).label("TESTING")
				.bodyStyler(TLspPaintState.REGULAR, mainStyler).bodyStyler(TLspPaintState.SELECTED, selStyler)
				.culling(false)
				.build();
		view.addLayer(lspLayer);
	}

	public ILspLayer getDrawingLayer() {
		return lspLayer;
	}
}
