package sg.gov.dsta.thickdemo.ui;

import javafx.scene.layout.GridPane;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;

public class FxGridPaneFactory implements ObjectFactory<GridPane> {
    @Override
    public GridPane getObject() throws BeansException {
        return new GridPane();
    }
}
