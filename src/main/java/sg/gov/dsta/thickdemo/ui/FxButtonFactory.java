package sg.gov.dsta.thickdemo.ui;

import javafx.scene.control.Button;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

@Component
public class FxButtonFactory implements ObjectFactory<Button> {
    @Override
    public Button getObject() throws BeansException {
        return new Button();
    }
}
