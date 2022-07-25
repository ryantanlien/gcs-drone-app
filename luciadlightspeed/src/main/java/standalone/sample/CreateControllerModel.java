package standalone.sample;

import com.luciad.shape.shape2D.ILcd2DEditableShape;
import com.luciad.shape.shape2D.TLcdLonLatBounds;
import com.luciad.view.lightspeed.ILspView;
import com.luciad.view.lightspeed.controller.manipulation.ALspCreateControllerModel;
import com.luciad.view.lightspeed.editor.TLspEditContext;
import com.luciad.view.lightspeed.layer.ILspInteractivePaintableLayer;
import com.luciad.view.lightspeed.layer.ILspLayer;

public class CreateControllerModel extends ALspCreateControllerModel {
    ILspLayer lspLayer;

    public CreateControllerModel(ILspLayer lspLayer) {
        this.lspLayer = lspLayer;
    }

    @Override
    public Object create(ILspView arg0, ILspLayer arg1) {
        ILcd2DEditableShape shape = new TLcdLonLatBounds();
        ShapeStyleHelper.applyStyleToShape(shape, arg1);
        return shape;
    }

    @Override
    public ILspInteractivePaintableLayer getLayer(ILspView arg0) {
        return (ILspInteractivePaintableLayer) lspLayer;
    }

    @Override
    public int getMaximumPointCount(TLspEditContext aEditContext) {
        return 10;
    }

    @Override
    public int getMinimumPointCount(TLspEditContext aEditContext) {
        return 0;
    }
}
