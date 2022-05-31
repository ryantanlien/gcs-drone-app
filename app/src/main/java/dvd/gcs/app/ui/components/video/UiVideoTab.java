package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import dvd.gcs.app.ui.api.UiSwappableLayeredPane;
import dvd.gcs.app.ui.components.UiBasePanel;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("UiVideoTab")
@Lazy
public class UiVideoTab extends UiSwappableLayeredPane {

    @Autowired
    BeanFactory beanFactory;

    @Autowired
    public UiVideoTab(
            @Qualifier("UiVideoInterfacePanel") UiPane uiPane,
            @Qualifier("StackPane") Pane pane) {
        super(uiPane, pane);
        fillInnerParts();
    }

    private void fillInnerParts() {
        super.addInnerPane();
    }

    @Override
    public UiPane swap() {
        return beanFactory.getBeanProvider(UiBasePanel.class).getIfAvailable();
    }
}
